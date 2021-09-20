package medved.studio.pharmix.presentation.registration_second_step

import android.content.Context
import medved.studio.data.validator.FieldsValidator
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class RegistrationSecondStepPresenter(
    private val fieldsValidator: FieldsValidator,
    private val context: Context,
    val router: AppRouter
) : BasePresenterImpl<RegistrationSecondStepView>() {

    override fun attachView(view: RegistrationSecondStepView?) {
        super.attachView(view)
    }

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
}