package medved.studio.pharmix.presentation.login

import android.app.Activity
import android.content.Intent
import com.franmontiel.localechanger.LocaleChanger
import com.franmontiel.localechanger.utils.ActivityRecreationHelper
import medved.studio.data.validator.FieldsValidator
import medved.studio.domain.entities.HttpException
import medved.studio.domain.entities.ServerApiError
import medved.studio.domain.interactors.auth.AuthInteractor
import medved.studio.domain.utils.logging.ILogger
import medved.studio.pharmix.ext.data.CURRENTLY_ADDED_LANGUAGES
import medved.studio.pharmix.ext.data.SUPPORTED_LOCALES_CUSTOM
import medved.studio.pharmix.global.base.SocialLoginPresenter
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import medved.studio.pharmix.ui.activities.main.MainActivity
import medved.studio.pharmix.ui.fragments.login.CustomLanguage
import moxy.InjectViewState
import toothpick.InjectConstructor
import java.util.*
import kotlin.collections.ArrayList


@InjectConstructor
@InjectViewState
class LoginPresenter(
    override val authInteractor: AuthInteractor,
    private val fieldsValidator: FieldsValidator,
    override val router: AppRouter,
    private val logger: ILogger
) : SocialLoginPresenter<LoginView>() {

    var activity: Activity? = null

    lateinit var filterList: MutableList<CustomLanguage>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        authInteractor.getSocialTypes()
            .await(withProgress = false) { viewState.showSocialTypes(it) }
    }

    override fun attachView(view: LoginView?) {
        super.attachView(view)
        filterList = ArrayList()
        viewState.showLanguages(SUPPORTED_LOCALES_CUSTOM.filter {
            CURRENTLY_ADDED_LANGUAGES.contains(it.locale)
        })
    }

    fun onLanguageChange(locale: Locale) {
        LocaleChanger.resetLocale()
        LocaleChanger.setLocale(locale)
        activity?.finishAffinity()
        activity?.startActivity(Intent(activity, MainActivity::class.java))
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


}