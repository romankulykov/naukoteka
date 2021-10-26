package uddug.com.naukoteka.presentation.login

import uddug.com.data.validator.FieldsValidator
import uddug.com.domain.entities.HttpException
import uddug.com.domain.entities.ServerApiError
import uddug.com.domain.interactors.auth.AuthInteractor
import uddug.com.domain.utils.logging.ILogger
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens
import moxy.InjectViewState
import toothpick.InjectConstructor


@InjectConstructor
@InjectViewState
class LoginPresenter(
    override val authInteractor: AuthInteractor,
    private val fieldsValidator: FieldsValidator,
    override val router: AppRouter,
    private val logger: ILogger
) : SocialLoginPresenter<LoginView>() {


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
        router.navigateTo(Screens.Chat())
    }

    fun exit() {
        router.exit()
    }


}