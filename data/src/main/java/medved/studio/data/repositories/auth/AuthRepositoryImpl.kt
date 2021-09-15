package medved.studio.data.repositories.auth

import medved.studio.data.services.auth.AuthApiService
import medved.studio.domain.repositories.auth.AuthRepository
import toothpick.InjectConstructor

@InjectConstructor
class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val mapper: AuthRepositoryMapper,
) : AuthRepository {

}