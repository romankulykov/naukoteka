package medved.studio.pharmix.presentation.login

import medved.studio.data.validator.FieldsValidator
import medved.studio.domain.interactors.auth.AuthInteractor
import medved.studio.domain.repositories.auth.models.SocialType
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import medved.studio.pharmix.ui.AppConfigs
import moxy.InjectViewState
import toothpick.InjectConstructor


@InjectConstructor
@InjectViewState
class LoginPresenter(
    private val authInteractor: AuthInteractor,
    private val fieldsValidator: FieldsValidator,
    val router: AppRouter
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
        //authInteractor.login(email, pass)
        authInteractor.testLogin()
            .await { viewState.showSuccessLogin() }
    }

    fun toRegistration() {
        router.navigateTo(Screens.RegistrationFirstStep())
    }

    fun exit() {
        router.exit()
    }

    fun authSocial(socialType: SocialType) {
        router.navigateTo(
            Screens.OpenBrowser(
                AppConfigs.authSocialUrl(
                    socialType,
                    "https://stage.naukotheka.ru/login-actions/social-login?social_type=${socialType.raw}"
                )
            )
        )
    }


}