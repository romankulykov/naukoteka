package medved.studio.domain.interactors.auth

import io.reactivex.Completable
import medved.studio.domain.SchedulersProvider
import medved.studio.domain.repositories.auth.AuthRepository
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

}