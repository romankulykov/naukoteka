package uddug.com.domain.interactors.dialogs

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.domain.SchedulersProvider
import uddug.com.domain.entities.MediaCategory
import uddug.com.domain.repositories.dialogs.DialogsRepository
import uddug.com.domain.repositories.dialogs.models.*
import uddug.com.domain.repositories.files.FilesRepository
import uddug.com.domain.repositories.websockets.WebSocketRepository
import uddug.com.domain.repositories.websockets.models.SocketChatMessageResponseDto
import uddug.com.domain.repositories.websockets.models.SocketFileRequestDto
import uddug.com.domain.repositories.websockets.models.SocketPushMessageRequestDto
import java.io.File
import java.util.*

@InjectConstructor
class DialogsInteractor(
    private val dialogsRepository: DialogsRepository,
    private val webSocketRepository: WebSocketRepository,
    private val filesRepository: FilesRepository,
    private val schedulers: SchedulersProvider
) {

    var messages = linkedSetOf<ChatMessage>()
    var chatPreview: ChatPreview? = null

    fun getDialogs(limit: Int, lastMessageId: Int? = null): Single<ChatsPreview> {
        return dialogsRepository.getChatsPreview(limit, lastMessageId)
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
            .flatMap { (id, _) -> dialogsRepository.getChatDetailInfo(id) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun getChatDetailInfo(id: Int): Single<ChatPreview> {
        return dialogsRepository.getChatDetailInfo(id)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun clearDialog(dialogId: Int): Completable {
        return dialogsRepository.clearDialog(dialogId)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun deletePersonalDialog(dialogId: Int): Completable {
        return dialogsRepository.deleteDialog(dialogId)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun togglePin(chatListEntity: ChatPreview): Single<ChatPreview> {
        return (if (chatListEntity.isPinned) dialogsRepository.unPinChat(chatListEntity.dialogId)
        else dialogsRepository.pinChat(chatListEntity.dialogId)).toSingle {}
            .flatMap { dialogsRepository.getChatDetailInfo(chatListEntity.dialogId) }
            .map { chatListEntity.apply { isPinned = it.isPinned } }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun openSocket() = webSocketRepository.open()
    fun closeSocket() = webSocketRepository.close()

    // TODO pass chatPreview here, earlier we should inject it in presenter as module
    fun observe(): Flowable<ChatMessage> {
        return webSocketRepository.observe(SocketChatMessageResponseDto::class.java, "")
            .map {
                it.run {
                    val user = messages.find { it.user?.userId == owner }?.user
                        ?: chatPreview?.users?.find { it.userId == owner }
                        ?: chatPreview?.interlocutor

                    val message = ChatMessage(
                        id = id,
                        text = text,
                        type = MessageType.values().find { it.type == cType } ?: MessageType.TEXT,
                        files = files?.map {
                            it.run {
                                AttachmentChatPreview(
                                    id = id,
                                    path = path,
                                    fileType = filetype,
                                    filename = filename,
                                    contentType = ContentType.values()
                                        .find { it.type == contenttype })
                            }
                        } ?: emptyList(),
                        ownerId = owner,
                        createdAt = Calendar.getInstance(),
                        read = emptyList(),
                        user = user,
                        chatPreview = chatPreview
                    )
                    messages.add(message)
                    message
                }
            }
            .observeOn(schedulers.ui())
    }

    fun pushTextMessage(
        chatPreview: ChatPreview,
        text: String? = null,
        files: List<File>? = null
    ): Completable {
        this.chatPreview = chatPreview

        if (!files.isNullOrEmpty()) {
            return filesRepository.sendFiles(files)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .flatMapCompletable {
                    val fileType = 100
                    webSocketRepository.emit(
                        SocketPushMessageRequestDto(
                            dialog = chatPreview.dialogId,
                            cType = 1,
                            text = text,
                            files = it.map { SocketFileRequestDto(it.id, fileType) })
                    )
                }
        }

        return webSocketRepository.emit(
            SocketPushMessageRequestDto(
                dialog = chatPreview.dialogId,
                cType = 1,
                text = text
            )
        )
    }

    fun searchDialogs(query: String, limit: Int): Single<List<SearchDialogs>> {
        return dialogsRepository.searchDialogs(query, limit)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun searchMediaContent(
        dialogId: Int,
        category: MediaCategory,
        lastMessageId: Int? = null,
        limit: Int? = null
    ): Single<List<SearchMedia>> {
        return dialogsRepository.searchMediaContent(
            dialogId,
            MediaCategory.values().find { it == category }!!.category,
            lastMessageId,
            limit
        )
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun searchMessages(query: String, limit: Int): Observable<List<SearchMessagesInDialogs>> {
        val pageLimit = 10
        return dialogsRepository.getChatsPreview(pageLimit)
            .flattenAsObservable { it.dialogs }
            .flatMap {
                dialogsRepository.searchMessagesInDialogs(it.dialogId, query, pageLimit)
                    .toObservable()
            }
            .filter { it.isNotEmpty() }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun searchMessagesInDialog(
        dialogId: Int,
        query: String,
        limit: Int
    ): Single<List<SearchMessagesInDialogs>> {
        return dialogsRepository.searchMessagesInDialogs(dialogId, query, limit)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

}