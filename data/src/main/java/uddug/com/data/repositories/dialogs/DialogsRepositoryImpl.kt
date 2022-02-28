package uddug.com.data.repositories.dialogs

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import toothpick.InjectConstructor
import uddug.com.data.cache.user_uuid.UserUUIDCache
import uddug.com.data.services.DialogsApiService
import uddug.com.data.services.models.request.dialogs.CreateDialogRequestDto
import uddug.com.domain.repositories.dialogs.DialogsRepository
import uddug.com.domain.repositories.dialogs.models.*

@InjectConstructor
class DialogsRepositoryImpl(
    private val dialogsApiService: DialogsApiService,
    private val dialogsRepositoryMapper: DialogsRepositoryMapper,
    private val userUUID: UserUUIDCache,
) : DialogsRepository {

    override fun getChatsPreview(
        limit: Int, lastMessageId: Int?,
    ): Single<ChatsPreview> {
        return dialogsApiService.dialogs(limit, lastMessageId)
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
                    .map { chatMessage ->
                        val user = if (chatMessage.ownerId == null) {
                            // messages with null ownerId means that it is system message
                            UserChatPreview(null, userUUID.requireEntity, false, "", "")
                        } else if (chatPreview.dialogType == DialogType.GROUP) {
                            chatPreview.users?.find { it.userId == chatMessage.ownerId }
                        } else {
                            if (userUUID.entity == chatMessage.ownerId) {
                                UserChatPreview(null, userUUID.requireEntity, false, "", "")
                            } else {
                                chatPreview.interlocutor
                            }
                        }
                        dialogsRepositoryMapper.mapDialogDetailToDomain(chatMessage, user)
                    }
            }
    }

    override fun createDialog(name: String?, uuids: List<String>): Single<Pair<Int, Boolean>> {
        return dialogsApiService.dialogCreate(CreateDialogRequestDto(name, uuids))
            .map { Pair(it.id, it.isAlreadyExist == true) }
    }

    override fun deleteDialog(dialogId: Int): Completable {
        return dialogsApiService.deleteDialog(dialogId)
    }

    override fun pinChat(dialogId: Int): Completable {
        return dialogsApiService.pinDialog(dialogId)
    }

    override fun unPinChat(dialogId: Int): Completable {
        return dialogsApiService.unpinDialog(dialogId)
    }

    override fun searchDialogs(query: String, limit: Int): Single<List<SearchDialogs>> {
        return dialogsApiService.searchDialogs(query, limit)
            .onErrorReturnItem(emptyList())
            .map { it.map { dialogsRepositoryMapper.mapSearchDialog(it) } }
    }

    override fun searchMessagesInDialogs(
        dialogId: Int,
        query: String,
        limit: Int
    ): Single<List<SearchMessagesInDialogs>> {
        return dialogsApiService.searchMessagesInDialog(dialogId, query, limit)
            .onErrorReturnItem(emptyList())
            .zipWith(dialogsApiService.getChatDetailInfo(dialogId))
            .map { (foundMessages, chatInfo) ->
                foundMessages.map { messageDto ->
                    val message = messageDto.toChatMessage()
                    dialogsRepositoryMapper.mapMessageToSearchMessageDomain(message, chatInfo)
                }
            }
    }


}