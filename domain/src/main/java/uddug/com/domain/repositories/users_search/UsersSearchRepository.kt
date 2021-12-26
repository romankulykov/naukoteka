package uddug.com.domain.repositories.users_search

import io.reactivex.Single
import uddug.com.domain.repositories.dialogs.models.UserChatPreview

interface UsersSearchRepository {
    fun searchUsers(query: String): Single<List<UserChatPreview>>
}