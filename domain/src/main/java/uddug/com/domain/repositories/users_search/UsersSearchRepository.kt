package uddug.com.domain.repositories.users_search

import io.reactivex.Single
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.domain.repositories.users_search.models.UserStatus

interface UsersSearchRepository {
    fun searchUsers(query: String): Single<List<UserChatPreview>>
    fun usersStatus(usersUUIDs : List<String>) : Single<List<UserStatus>>
}