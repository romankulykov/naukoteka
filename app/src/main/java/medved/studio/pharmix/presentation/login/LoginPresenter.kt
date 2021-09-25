package medved.studio.pharmix.presentation.login

import medved.studio.data.validator.FieldsValidator
import medved.studio.domain.entities.HttpException
import medved.studio.domain.entities.ServerApiError
import medved.studio.domain.interactors.auth.AuthInteractor
import medved.studio.domain.repositories.auth.models.SocialType
import medved.studio.domain.utils.logging.ILogger
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import medved.studio.pharmix.ui.AppConfigs
import medved.studio.pharmix.ui.IntentKeys
import medved.studio.pharmix.ui.custom.square_toast.SquareToast
import medved.studio.pharmix.ui.custom.square_toast.ToastInfo
import moxy.InjectViewState
import toothpick.InjectConstructor


@InjectConstructor
@InjectViewState
class LoginPresenter(
    private val authInteractor: AuthInteractor,
    private val fieldsValidator: FieldsValidator,
    val router: AppRouter,
    private val logger : ILogger
) : BasePresenterImpl<LoginView>() {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        authInteractor.getSocialTypes()
            .await(withProgress = false) { viewState.showSocialTypes(it) }
    }

    fun isValidFields(email: String, password: String) {
        viewState.showButtonState(
            fieldsValidator.isValidEmail(email) &&
                    fieldsValidator.isNotEmpty(password)
        )
    }

    fun enter(email: String, pass: String) {
        authInteractor.login(email, pass)
            .await(onError = {
                if (it is HttpException && it.statusCode == ServerApiError.InvalidCredentials) {
                    viewState.showErrorCredentials(true)
                } else onError(it)
            }, onComplete = { viewState.showSuccessLogin() })
    }

    fun toRegistration() {
        logger.debug("click toRegistration")
        router.navigateTo(Screens.RegistrationFirstStep())
    }

    fun toPasswordRecovery() {
        logger.debug("click toPasswordRecovery")
        router.navigateTo(Screens.PasswordRecovery())
    }

    fun exit() {
        router.exit()
    }

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
                viewState.showMessage(ToastInfo("Success", type = SquareToast.Type.SUCCESS))
            }
    }


}