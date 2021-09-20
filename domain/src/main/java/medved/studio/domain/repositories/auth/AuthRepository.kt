package medved.studio.domain.repositories.auth

import io.reactivex.Completable

interface AuthRepository {

    fun loginEmail(login: String, password: String): Completable
    fun register(login: String, password: String): Completable
    fun checkConfirmRegistration(key: String): Completable
    fun checkEmailForFree(email : String) : Completable

}