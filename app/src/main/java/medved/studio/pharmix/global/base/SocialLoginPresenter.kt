package medved.studio.pharmix.global.base

import com.github.terrakok.cicerone.Router
import medved.studio.domain.interactors.auth.AuthInteractor
import medved.studio.domain.repositories.auth.models.SocialType
import medved.studio.pharmix.global.views.InformativeView
import medved.studio.pharmix.global.views.LoadingView
import medved.studio.pharmix.navigation.Screens
import medved.studio.pharmix.ui.AppConfigs
import medved.studio.pharmix.ui.IntentKeys
import medved.studio.pharmix.ui.custom.square_toast.SquareToast
import medved.studio.pharmix.ui.custom.square_toast.ToastInfo
import moxy.MvpView


interface SocialLoginView : MvpView, InformativeView, LoadingView

abstract class SocialLoginPresenter<V : SocialLoginView> : BasePresenterImpl<V>() {

    abstract val router: Router
    abstract val authInteractor: AuthInteractor

    fun authSocial(socialType: SocialType) {
        router.run {
            setResultListener(Screens.RESULT_AUTH_SOCIAL) {
                (it as? IntentKeys.SocialAuthorization)?.let { socialAuthorization ->
                    socialAuth(socialAuthorization.socialType, socialAuthorization.key)
                }
            }
            navigateTo(
                Screens.WebViewAuth(
                    AppConfigs.authSocialUrl(
                        socialType,
                        "https://stage.naukotheka.ru/${AppConfigs.SOCIAL_LOGIN_ENDPOINT}?social_type=${socialType.raw}"
                    )
                )
            )
        }
    }

    fun socialAuth(socialType: SocialType, key: String) {
        authInteractor.authenticate(socialType, key)
            .await {
                if (viewState is LoadingView) {
                    viewState.showMessage(ToastInfo("Success", type = SquareToast.Type.SUCCESS))
                }
            }
    }


}