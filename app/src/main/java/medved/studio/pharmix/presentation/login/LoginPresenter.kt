package medved.studio.pharmix.presentation.login

import android.content.Context
import io.reactivex.disposables.Disposable
import medved.studio.data.validator.FieldsValidator
import medved.studio.domain.interactors.auth.AuthInteractor
import medved.studio.pharmix.global.base.BasePresenterImpl
import moxy.InjectViewState
import toothpick.InjectConstructor


@InjectConstructor
@InjectViewState
class LoginPresenter(
    private val authInteractor: AuthInteractor,
    private val fieldsValidator: FieldsValidator,
    private val context: Context,
) : BasePresenterImpl<LoginView>() {


    sealed class SocialAuth(open val token: String) {
        data class Facebook(override val token: String) : SocialAuth(token)
        data class Google(override val token: String) : SocialAuth(token)
        data class Vk(override val token: String) : SocialAuth(token)
    }


    companion object {
        const val GOOGLE_REQUEST_CODE_INTENT = 1234
        const val FACEBOOK_REQUEST_CODE_INTENT = 6539

        private const val SOCIAL_TYPE_VK = "vkontakte"
        private const val SOCIAL_TYPE_FB = "facebook"
    }

    private var countDownTimer: Disposable? = null
    private var verificationId: String = ""

    override fun attachView(view: LoginView?) {
        super.attachView(view)
        /*authInteractor.testLogin()
            .await {

            }*/
    }


}