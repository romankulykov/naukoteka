package medved.studio.domain.interactors.auth

import io.reactivex.Completable
import io.reactivex.Single
import medved.studio.domain.SchedulersProvider
import medved.studio.domain.entities.PasswordRequirementsEntity
import medved.studio.domain.repositories.auth.AuthRepository
import medved.studio.domain.repositories.auth.models.SessionAttributes
import medved.studio.domain.repositories.auth.models.SocialType
import toothpick.InjectConstructor

@InjectConstructor
class AuthInteractor(
    private val authRepository: AuthRepository,
    private val schedulers: SchedulersProvider,
) {

    fun testLogin() = login("a@example.local", "Abcd!2345")

    fun login(login: String, password: String): Completable {
        return authRepository.loginEmail(login, password)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun sendLetterToRecovery(email: String): Completable {
        return authRepository.sendLetterToRecoveryPassword(email)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun register(login: String, password: String): Completable {
        return authRepository.register(login, password)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun checkToken(key: String): Completable {
        return authRepository.checkConfirmRegistration(key)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun checkTokenToRecovery(key: String): Single<SessionAttributes> {
        return authRepository.checkConfirmRecovery(key)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun setNewPassword(password: String, sessionAttributes: SessionAttributes): Completable {
        return authRepository.setNewPassword(sessionAttributes, password)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun checkEmailFree(email: String): Completable {
        return authRepository.checkEmailForFree(email)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun getSocialTypes(): Single<List<SocialType>> {
        return authRepository.socialTypes()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun authenticate(socialType: SocialType, key: String): Completable {
        return authRepository.socialAuth(socialType, key)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }


}