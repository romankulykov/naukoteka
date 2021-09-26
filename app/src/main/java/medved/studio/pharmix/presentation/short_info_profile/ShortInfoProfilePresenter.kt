package medved.studio.pharmix.presentation.short_info_profile

import android.content.Context
import medved.studio.data.validator.FieldsValidator
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class ShortInfoProfilePresenter(
    private val fieldsValidator: FieldsValidator,
    private val context: Context,
    val router: AppRouter
) : BasePresenterImpl<ShortInfoProfileView>() {

    override fun attachView(view: ShortInfoProfileView?) {
        super.attachView(view)
    }

    fun isValidFields(surname: String, name: String, patronymic: String, link: String) {
        viewState.showButtonState(
            fieldsValidator.isNotEmpty(surname) &&
                    fieldsValidator.isNotEmpty(name) &&
                    fieldsValidator.isNotEmpty(patronymic) &&
                    fieldsValidator.isNotEmpty(link)
        )
    }

    fun nextStep() {
        router.newRootScreen(Screens.Login())
    }
}