package medved.studio.data.repositories.auth

import medved.studio.data.services.models.response.auth.CheckTokenKeyResponseDto
import medved.studio.domain.repositories.auth.models.SessionAttributes
import toothpick.InjectConstructor

@InjectConstructor
class AuthRepositoryMapper {

    fun mapSessionAttributesToDomain(dto: CheckTokenKeyResponseDto) = dto.run {
        SessionAttributes(
            authSessionId = authSessionId!!,
            clientId = clientId!!,
            key = key!!,
            tabId = tabId!!
        )
    }

}