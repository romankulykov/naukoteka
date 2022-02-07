package uddug.com.domain.repositories.dialogs

import io.reactivex.Completable
import io.reactivex.Single
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.ChatsPreview

interface DialogsRepository {

    fun getChatsPreview(limit: Int, lastMessageId: Int? = null): Single<ChatsPreview>
    fun getChatDetailInfo(id: Int): Single<ChatPreview>
    fun getChatMessages(
        chatPreview: ChatPreview,
        lastMessageId: Int? = null,
        limit: Int = 10,
    ): Single<List<ChatMessage>>

    fun createDialog(name: String?, uuids: List<String>): Single<Pair<Int, Boolean>>
    fun deleteDialog(dialogId: Int): Completable
    fun pinChat(dialogId: Int) : Completable
    fun unPinChat(dialogId: Int) : Completable

}