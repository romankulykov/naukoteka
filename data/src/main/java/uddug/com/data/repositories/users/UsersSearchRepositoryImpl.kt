package uddug.com.data.repositories.users

import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.data.services.UsersSearchApiService
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.domain.repositories.users_search.UsersSearchRepository

@InjectConstructor
class UsersSearchRepositoryImpl(
    private val usersSearchApiService: UsersSearchApiService,
    private val usersSearchRepositoryMapper: UsersSearchRepositoryMapper,
) : UsersSearchRepository {

    override fun searchUsers(query: String): Single<List<UserChatPreview>> {
        return usersSearchApiService.usersSearch(query)
            .map { usersSearchRepositoryMapper.mapUsersToDomain(it) }
    }

}