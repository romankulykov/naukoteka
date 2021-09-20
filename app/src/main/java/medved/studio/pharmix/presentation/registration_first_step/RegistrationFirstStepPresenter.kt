package medved.studio.pharmix.presentation.registration_first_step

import android.content.Context
import medved.studio.data.validator.FieldsValidator
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class RegistrationFirstStepPresenter(
    private val fieldsValidator: FieldsValidator,
    private val context: Context,
    val router: AppRouter
) : BasePresenterImpl<RegistrationFirstStepView>() {

    override fun attachView(view: RegistrationFirstStepView?) {
        super.attachView(view)
    }

    fun isValidField(email: String) {
        viewState.showButtonState(
            fieldsValidator.isValidEmail(email)
        )
    }

    fun onBackPressed() {
        router.newRootScreen(Screens.Login())
    }
}