package uddug.com.data.repositories.dialogs

import io.reactivex.Completable
import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.data.services.DialogsApiService
import uddug.com.data.services.models.request.dialogs.CreateDialogRequestDto
import uddug.com.domain.repositories.dialogs.DialogsRepository
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.ChatsPreview

@InjectConstructor
class DialogsRepositoryImpl(
    private val dialogsApiService: DialogsApiService,
    private val dialogsRepositoryMapper: DialogsRepositoryMapper,
) : DialogsRepository {

    override fun getChatsPreview(): Single<ChatsPreview> {
        return dialogsApiService.dialogs()
            .map { dialogsRepositoryMapper.mapChatsPreviewToDomain(it) }
    }

    override fun getChatDetailInfo(id: Int): Single<ChatPreview> {
        return dialogsApiService.getChatDetailInfo(id)
            .map { dialogsRepositoryMapper.mapChatPreviewToDomain(it) }
    }

    override fun getChatMessages(
        chatPreview: ChatPreview,
        lastMessageId: Int?,
        limit: Int
    ): Single<List<ChatMessage>> {
        return dialogsApiService.dialogsDetail(chatPreview.dialogId, lastMessageId, limit)
            .map { messages ->
                messages
                    // TODO think up maybe null owner id it is deleted account ?
                    .filter { !it.ownerId.isNullOrBlank() }
                    .map { chatMessage ->
                        val user = chatPreview.users?.find { it.userId == chatMessage.ownerId }
                        dialogsRepositoryMapper.mapDialogDetailToDomain(chatMessage, user)
                    }
            }
    }

    override fun createDialog(name: String?, uuids: List<String>): Single<Int> {
        return dialogsApiService.dialogCreate(CreateDialogRequestDto(name, uuids))
            .map { it.id }
    }

    override fun deleteDialog(dialogId: Int): Completable {
        return dialogsApiService.deleteDialog(dialogId)
    }

}