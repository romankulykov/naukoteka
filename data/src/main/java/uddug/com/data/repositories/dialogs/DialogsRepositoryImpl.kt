package uddug.com.data.repositories.dialogs

import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.data.services.DialogsApiService
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

    override fun getChatDetail(chatPreview: ChatPreview): Single<List<ChatMessage>> {
        return dialogsApiService.dialogsDetail(chatPreview.dialogId)
            .map { messages ->
                messages
                     // TODO think up maybe null owner id it is deleted account ?
                    .filter { !it.ownerId.isNullOrBlank() }
                    .map { dialogsRepositoryMapper.mapDialogDetailToDomain(it, chatPreview) }
            }
    }

}