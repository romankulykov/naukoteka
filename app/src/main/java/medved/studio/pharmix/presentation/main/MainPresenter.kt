package medved.studio.pharmix.presentation.main

import medved.studio.domain.interactors.auth.AuthInteractor
import medved.studio.domain.repositories.auth.models.SocialType
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.ui.custom.square_toast.SquareToast
import medved.studio.pharmix.ui.custom.square_toast.ToastInfo
import toothpick.InjectConstructor

@InjectConstructor
class MainPresenter(private val authInteractor: AuthInteractor) : BasePresenterImpl<MainView>() {

    fun checkToken(key: String) {
        authInteractor.checkToken(key)
            .await {
                viewState.showMessage(ToastInfo("Success", type = SquareToast.Type.SUCCESS))
            }
    }

    fun socialAuth(socialType: SocialType, key: String) {
        authInteractor.authenticate(socialType, key)
            .await {
                viewState.showMessage(ToastInfo("Success", type = SquareToast.Type.SUCCESS))
            }
    }

}