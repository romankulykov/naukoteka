package uddug.com.data.repositories.users

import toothpick.InjectConstructor
import uddug.com.data.repositories.dialogs.DialogsRepositoryMapper
import uddug.com.data.services.models.response.users_search.UsersSearchResponseDto

@InjectConstructor
class UsersSearchRepositoryMapper(
    private val dialogsRepositoryMapper: DialogsRepositoryMapper
) {

    fun mapUsersToDomain(dto: UsersSearchResponseDto) = dto.run {
        users.map { dialogsRepositoryMapper.mapUserChatToDomain(it)!! }
    }

}