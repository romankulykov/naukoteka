package uddug.com.data.repositories.users

import toothpick.InjectConstructor
import uddug.com.data.repositories.dialogs.DialogsRepositoryMapper
import uddug.com.data.services.models.response.users_search.UsersSearchResponseDto
import uddug.com.data.services.models.response.users_status.UsersStatusResponseDto
import uddug.com.domain.repositories.users_search.models.UserStatus
import uddug.com.domain.repositories.users_search.models.UserStatusDetail

@InjectConstructor
class UsersSearchRepositoryMapper(
    private val dialogsRepositoryMapper: DialogsRepositoryMapper
) {

    fun mapUsersToDomain(dto: UsersSearchResponseDto) = dto.run {
        users.map { dialogsRepositoryMapper.mapUserChatToDomain(it)!! }
    }

    fun mapUsersStatusToDomain(dto: List<UsersStatusResponseDto>) = dto.run {
        map {
            it.run {
                UserStatus(
                    userId = userId,
                    status = status.run {
                        UserStatusDetail(
                            isOnline = isOnline,
                            lastSeen = lastSeen
                        )
                    })
            }
        }
    }

}