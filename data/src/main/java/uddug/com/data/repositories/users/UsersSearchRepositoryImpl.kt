package uddug.com.data.repositories.users

import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.data.services.UsersSearchApiService
import uddug.com.data.services.models.request.user_status.UserStatusRequestDto
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.domain.repositories.users_search.UsersSearchRepository
import uddug.com.domain.repositories.users_search.models.UserStatus

@InjectConstructor
class UsersSearchRepositoryImpl(
    private val usersSearchApiService: UsersSearchApiService,
    private val usersSearchRepositoryMapper: UsersSearchRepositoryMapper,
) : UsersSearchRepository {

    override fun searchUsers(query: String): Single<List<UserChatPreview>> {
        return usersSearchApiService.usersSearch(query)
            .map { usersSearchRepositoryMapper.mapUsersToDomain(it) }
    }

    override fun usersStatus(usersUUIDs: List<String>): Single<List<UserStatus>> {
        return usersSearchApiService.userStatus(UserStatusRequestDto(usersUUIDs))
            .map { usersSearchRepositoryMapper.mapUsersStatusToDomain(it) }
    }
}