package medved.studio.pharmix.presentation.registration_first_step

import medved.studio.data.validator.FieldsValidator
import medved.studio.domain.interactors.auth.AuthInteractor
import medved.studio.pharmix.di.DI
import medved.studio.pharmix.di.modules.RegistrationModule
import medved.studio.pharmix.di.modules.SimpleRegistration
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import medved.studio.pharmix.global.base.SocialLoginPresenter
import moxy.InjectViewState
import toothpick.InjectConstructor
import toothpick.ktp.KTP

@InjectConstructor
@InjectViewState
class RegistrationFirstStepPresenter(
    private val fieldsValidator: FieldsValidator,
    private val simpleRegistration: SimpleRegistration,
    override val router: AppRouter,
    override val authInteractor: AuthInteractor,
) : SocialLoginPresenter<RegistrationFirstStepView>() {

    init {
        val scope = getScope().openSubScope(DI.REGISTRATION_SCOPE)
        scope.installModules(RegistrationModule())
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        authInteractor.getSocialTypes()
            .await(withProgress = false) { viewState.showSocialTypes(it) }
    }

    fun isValidField(email: String) {
        viewState.showButtonState(
            fieldsValidator.isValidEmail(email)
        )
    }

    fun exit() {
        KTP.closeScope(DI.REGISTRATION_SCOPE)
        router.exit()
    }

    fun nextStep(login: String) {
        authInteractor.checkEmailFree(login)
            .await {
                simpleRegistration.login = login
                router.navigateTo(Screens.RegistrationSecondStep())
            }
    }
}