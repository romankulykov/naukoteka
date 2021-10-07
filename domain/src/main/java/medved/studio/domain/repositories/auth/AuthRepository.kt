package medved.studio.domain.repositories.auth

import io.reactivex.Completable
import io.reactivex.Single
import medved.studio.domain.repositories.auth.models.SessionAttributes
import medved.studio.domain.repositories.auth.models.SocialType

interface AuthRepository {

    fun loginEmail(login: String, password: String): Completable
    fun register(login: String, password: String): Completable
    fun checkConfirmRegistration(key: String): Completable
    fun checkConfirmRecovery(key: String): Single<SessionAttributes>
    fun setNewPassword(sessionAttributes: SessionAttributes, password: String): Completable
    fun checkEmailForFree(email: String): Completable
    fun socialTypes(): Single<List<SocialType>>
    fun socialAuth(socialType: SocialType, key: String): Completable
    fun sendLetterToRecoveryPassword(email: String): Completable
}