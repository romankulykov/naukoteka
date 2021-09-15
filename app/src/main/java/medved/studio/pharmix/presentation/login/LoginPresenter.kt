package medved.studio.pharmix.presentation.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import medved.studio.data.validator.FieldsValidator
import medved.studio.domain.entities.PrimitiveWrapper
import medved.studio.domain.interactors.auth.AuthInteractor
import medved.studio.pharmix.R
import medved.studio.pharmix.di.IsFromFilterPickCity
import medved.studio.pharmix.di.IsFromOrder
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.Screens
import medved.studio.pharmix.navigation.TabRouterHolder
import moxy.InjectViewState
import toothpick.InjectConstructor
import java.util.concurrent.TimeUnit


@InjectConstructor
@InjectViewState
class LoginPresenter(
    private val authInteractor: AuthInteractor,
    private val fieldsValidator: FieldsValidator,
    private val context: Context,
    @IsFromOrder isFromOrderWrapper: PrimitiveWrapper<Boolean>,
) : BasePresenterImpl<LoginView>() {

    private val isFromOrder = isFromOrderWrapper.value


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
    }



}