package medved.studio.data.repositories.auth

import io.reactivex.Completable
import medved.studio.data.services.auth.AuthApiService
import medved.studio.data.services.models.request.auth.AuthRequestDto
import medved.studio.data.services.models.request.auth.CheckTokenKeyDto
import medved.studio.data.services.models.request.auth.RegisterRequestDto
import medved.studio.domain.repositories.auth.AuthRepository
import toothpick.InjectConstructor

@InjectConstructor
class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val mapper: AuthRepositoryMapper,
) : AuthRepository {


    override fun loginEmail(login: String, password: String): Completable {
        return authApiService.login(AuthRequestDto(login, password))
            .flatMapCompletable { Completable.complete() }
    }

    override fun register(login: String, password: String): Completable {
        return authApiService.register(RegisterRequestDto(login, password))
            .flatMapCompletable { Completable.complete() }
    }

    override fun checkConfirmRegistration(key: String): Completable {
        return authApiService.checkTokenKey(CheckTokenKeyDto(key))
            .flatMapCompletable {
                Completable.complete()
            }

    }


}