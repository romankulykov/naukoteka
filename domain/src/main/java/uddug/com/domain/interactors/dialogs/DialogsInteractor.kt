package uddug.com.domain.interactors.dialogs

import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.domain.SchedulersProvider
import uddug.com.domain.repositories.dialogs.DialogsRepository
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.ChatsPreview
import uddug.com.domain.repositories.dialogs.models.MessageType
import uddug.com.domain.repositories.websockets.WebSocketRepository
import uddug.com.domain.repositories.websockets.models.SocketMessageResponseDto
import uddug.com.domain.repositories.websockets.models.SocketPushMessageRequestDto
import java.util.*

@InjectConstructor
class DialogsInteractor(
    private val dialogsRepository: DialogsRepository,
    private val webSocketRepository: WebSocketRepository,
    private val gson: Gson,
    private val schedulers: SchedulersProvider
) {

    var messages = linkedSetOf<ChatMessage>()

    fun getDialogs(): Single<ChatsPreview> {
        return dialogsRepository.getChatsPreview()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun getDialogMessages(
        chatPreview: ChatPreview,
        limit: Int = 10
    ): Single<List<ChatMessage>> {
        return dialogsRepository.getChatMessages(
            chatPreview,
            messages.minByOrNull { it.id }?.id,
            limit
        )
            .doOnSuccess { messages.addAll(it) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun createDialog(name: String, uuids: List<String>): Single<ChatPreview> {
        return makeDialog(name, uuids)
    }

    fun createPersonalChat(opponentUUID: String): Single<ChatPreview> {
        return makeDialog(uuids = listOf(opponentUUID))
    }

    private fun makeDialog(name: String? = null, uuids: List<String>): Single<ChatPreview> {
        return dialogsRepository.createDialog(name, uuids)
            .flatMap { dialogsRepository.getChatDetailInfo(it) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun deletePersonalDialog(dialogId: Int): Completable {
        return dialogsRepository.deleteDialog(dialogId)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun openSocket() = webSocketRepository.open()
    fun closeSocket() = webSocketRepository.close()

    fun observe(): Flowable<ChatMessage> {
        return webSocketRepository.observe(SocketMessageResponseDto::class.java, "")
            .map {
                it.run {
                    val message = ChatMessage(
                        id,
                        text,
                        MessageType.values().find { it.type == cType } ?: MessageType.TEXT,
                        emptyList(),
                        owner,
                        Calendar.getInstance(),
                        emptyList(),
                        messages.find { it.user?.userId == owner }?.user
                    )
                    messages.add(message)
                    message
                }
            }
            .observeOn(schedulers.ui())
    }

    fun pushTextMessage(dialogId: Int, message: String): Completable {
        return webSocketRepository.emit(SocketPushMessageRequestDto(dialogId, 1, message))
    }

}