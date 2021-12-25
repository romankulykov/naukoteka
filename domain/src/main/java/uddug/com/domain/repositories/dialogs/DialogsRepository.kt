package uddug.com.domain.repositories.dialogs

import io.reactivex.Single
import uddug.com.domain.repositories.dialogs.models.ChatsPreview

interface DialogsRepository {

    fun getChatsPreview() : Single<ChatsPreview>

}