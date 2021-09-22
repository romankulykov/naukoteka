package medved.studio.pharmix.presentation.registration_second_step

import android.content.Context
import medved.studio.data.validator.FieldsValidator
import medved.studio.domain.interactors.auth.AuthInteractor
import medved.studio.pharmix.di.modules.RegistrationModule
import medved.studio.pharmix.di.modules.SimpleRegistration
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class RegistrationSecondStepPresenter(
    private val fieldsValidator: FieldsValidator,
    private val router: AppRouter,
    private val simpleRegistration: SimpleRegistration,
    private val authInteractor: AuthInteractor
) : BasePresenterImpl<RegistrationSecondStepView>() {


    fun isValidFields(password: String, passwordConfirmation: String) {
        viewState.showButtonState(
            fieldsValidator.isNotEmpty(password) &&
                    fieldsValidator.isNotEmpty(passwordConfirmation) &&
                    fieldsValidator.isEquals(password, passwordConfirmation)
        )
    }

    fun exit() {
        router.exit()
    }

    fun nextStep(password : String) {
        simpleRegistration.password = password
        authInteractor.register(simpleRegistration.login!!, simpleRegistration.password!!)
            .await {
                router.navigateTo(Screens.RegistrationThirdStep())
            }
    }
}