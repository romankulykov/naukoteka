package medved.studio.pharmix.presentation.main

import medved.studio.domain.interactors.auth.AuthInteractor
import medved.studio.pharmix.global.base.BasePresenterImpl
import toothpick.InjectConstructor

@InjectConstructor
class MainPresenter(private val authInteractor: AuthInteractor) : BasePresenterImpl<MainView>() {

    fun checkToken(key: String) {
        authInteractor.checkToken(key)
            .await {

            }
    }

}