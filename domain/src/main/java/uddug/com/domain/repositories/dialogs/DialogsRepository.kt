package uddug.com.domain.repositories.dialogs

import io.reactivex.Single
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.ChatsPreview

interface DialogsRepository {

    fun getChatsPreview() : Single<ChatsPreview>
    fun getChatDetailInfo(id : Int) : Single<ChatPreview>
    fun getChatMessages(chatPreview: ChatPreview): Single<List<ChatMessage>>
    fun createDialog(name : String, uuids : List<String>): Single<Int>

}