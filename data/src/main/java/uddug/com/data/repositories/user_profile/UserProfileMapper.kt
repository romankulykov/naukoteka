package uddug.com.data.repositories.user_profile

import toothpick.InjectConstructor
import uddug.com.data.cache.user_uuid.UserUUIDCache
import uddug.com.data.services.models.request.user_profile.UserProfileRequestDto
import uddug.com.domain.interactors.user_profile.model.ShortInfoUi

@InjectConstructor
class UserProfileMapper(
    private val userUUIDCache: UserUUIDCache,
) {

    fun mapDomainToDto(ui: ShortInfoUi) = ui.run {
        UserProfileRequestDto(
            id = userUUIDCache.entity,
            firstName = name,
            lastName = surname,
            middleName = if (middleName.isNullOrEmpty()) " " else middleName,
            // nick name should be more or equals 6 digits after id
            nickname = "id${nickname}"
        )
    }


}