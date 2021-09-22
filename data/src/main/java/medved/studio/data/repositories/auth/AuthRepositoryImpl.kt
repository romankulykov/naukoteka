package medved.studio.data.repositories.auth

import io.reactivex.Completable
import io.reactivex.Single
import medved.studio.data.services.auth.AuthApiService
import medved.studio.data.services.models.request.auth.AuthRequestDto
import medved.studio.data.services.models.request.auth.CheckEmailFreeDto
import medved.studio.data.services.models.request.auth.CheckTokenKeyDto
import medved.studio.data.services.models.request.auth.RegisterRequestDto
import medved.studio.data.services.models.request.reset.ResetRequestDto
import medved.studio.domain.entities.EmailNotFreeException
import medved.studio.domain.repositories.auth.AuthRepository
import medved.studio.domain.repositories.auth.models.SocialType
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

    override fun recoverPassportByEmail(email: String): Completable {
        return authApiService.passwordRecovery(ResetRequestDto("reset-credentials", email))
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

    override fun checkEmailForFree(email: String): Completable {
        return authApiService.checkEmailFree(CheckEmailFreeDto(email))
            .flatMapCompletable {
                if (it.emailIsFree) {
                    Completable.complete()
                } else {
                    throw EmailNotFreeException
                }
            }
    }

    override fun socialTypes(): Single<List<SocialType>> {
        return authApiService.getSocialTypes()
            .map {
                it.idpVariants.map { socialType ->
                    SocialType.values().find { it.raw == socialType }
                }.filterNotNull()
            }
    }

    override fun socialAuth(socialType: SocialType, key: String): Completable {
        return authApiService.authenticate(AuthRequestDto(socialLogin = socialType.raw, key = key))
            .flatMapCompletable {
                Completable.complete()
            }
    }


}