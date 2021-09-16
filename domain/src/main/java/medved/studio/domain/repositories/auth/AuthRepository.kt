package medved.studio.domain.repositories.auth

import io.reactivex.Completable
import io.reactivex.Single

interface AuthRepository {

    fun loginEmail(login : String, password : String) : Completable

}