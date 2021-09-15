package medved.studio.data.repositories.auth

import medved.studio.data.services.auth.models.AuthDto
import medved.studio.data.services.auth.models.TokenResponseDto
import medved.studio.data.services.auth.models.TokenDto
import medved.studio.domain.repositories.auth.models.BunchOfTokens
import medved.studio.domain.repositories.auth.models.Token
import toothpick.InjectConstructor

@InjectConstructor
class AuthRepositoryMapper {

    fun mapAuthTokenToDomain(dto: TokenResponseDto) = dto.run {
        BunchOfTokens(
            access = mapTokenToDomain(access),
            refresh = mapTokenToDomain(refresh)
        )
    }

    private fun mapTokenToDomain(dto: TokenDto) = dto.run { Token(token, expiry) }

    fun mapAuthToDomain(dto : AuthDto) = dto.run { mapAuthTokenToDomain(dto.result) }

}