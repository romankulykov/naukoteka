package medved.studio.data.repositories.auth

import io.reactivex.Completable
import io.reactivex.Single
import medved.studio.data.cache.cookies.CookiesCache
import medved.studio.data.cache.user_id.UserIdCache
import medved.studio.data.services.auth.AuthApiService
import medved.studio.data.services.models.request.auth.*
import medved.studio.data.services.models.request.user_profile.UserProfileRequestDto
import medved.studio.domain.entities.EmailNotFreeException
import medved.studio.domain.repositories.auth.AuthRepository
import medved.studio.domain.repositories.auth.models.SessionAttributes
import medved.studio.domain.repositories.auth.models.SocialType
import toothpick.InjectConstructor
import kotlin.random.Random

@InjectConstructor
class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val mapper: AuthRepositoryMapper,
    private val cookiesCache: CookiesCache,
    private val userIdCache: UserIdCache,
) : AuthRepository {


    override fun loginEmail(login: String, password: String): Completable {
        return authApiService.login(AuthRequestDto(login, password))
            .flatMapCompletable { Completable.complete() }
    }

    override fun sendLetterToRecoveryPassword(email: String): Completable {
        return authApiService.passwordRecovery(ResetRequestDto("reset-credentials", email))
    }

    override fun register(login: String, password: String): Single<Unit> {
        return authApiService.register(RegisterRequestDto(login, password))
            .flatMap {
                userIdCache.entity = it.userStatus.id
                Single.just(Unit)
            }
    }

    override fun checkConfirmRegistration(key: String): Single<Unit> {
        return authApiService.checkTokenKey(CheckTokenKeyDto(key))
            .flatMap { Single.just(Unit) }
    }

    override fun checkConfirmRecovery(key: String): Single<SessionAttributes> {
        return authApiService.checkTokenKey(CheckTokenKeyDto(key))
            .map { mapper.mapSessionAttributesToDomain(it) }
    }

    override fun setNewPassword(
        sessionAttributes: SessionAttributes,
        password: String
    ): Completable {
        return authApiService.checkTokenKey(
            sessionAttributes.run {
                CheckTokenKeyDto(key, clientId, tabId, authSessionId, password)
            }
        )
            .flatMapCompletable {
                if (!it.followUp) {
                    Completable.complete()
                } else {
                    throw Exception("Something went wrong during set new password")
                }
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

    override fun setUser(): Completable {
        return authApiService.setUser(
            //_nkts = cookiesCache.entity,
            userProfileRequestDto = UserProfileRequestDto(
                id = userIdCache.entity,
                firstName = "firstname",
                lastName = "asdasda",
                middleName = "adsadas",
                // nick name should be more or equals 6 digits after id
                nickname = "id${Random.nextInt(10000, 99999)}"
            )
        )
            .flatMapCompletable {
                Completable.complete()
            }
    }


}