package medved.studio.domain.interactors.auth

import medved.studio.domain.SchedulersProvider
import medved.studio.domain.repositories.auth.AuthRepository
import toothpick.InjectConstructor

@InjectConstructor
class AuthInteractor(
    private val authRepository: AuthRepository,
    private val schedulers: SchedulersProvider,
) {


}