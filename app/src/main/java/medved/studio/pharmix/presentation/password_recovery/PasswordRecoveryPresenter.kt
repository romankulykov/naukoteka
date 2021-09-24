package medved.studio.pharmix.presentation.password_recovery

import android.os.Handler
import android.os.Looper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import medved.studio.data.validator.FieldsValidator
import medved.studio.domain.interactors.auth.AuthInteractor
import medved.studio.domain.repositories.auth.models.SessionAttributes
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import medved.studio.pharmix.ui.AppConfigs
import medved.studio.pharmix.ui.custom.square_toast.SquareToast
import medved.studio.pharmix.ui.custom.square_toast.ToastInfo
import moxy.InjectViewState
import toothpick.InjectConstructor
import java.util.concurrent.TimeUnit

@InjectConstructor
@InjectViewState
class PasswordRecoveryPresenter(
    private val fieldsValidator: FieldsValidator,
    val router: AppRouter,
    private val authInteractor: AuthInteractor
) : BasePresenterImpl<PasswordRecoveryView>() {

    private var sessionAttrs: SessionAttributes? = null
    private var countDownTimer: Disposable? = null

    fun isValidEmail(email: String) {
        viewState.showButtonState(
            fieldsValidator.isValidEmail(email)
        )
    }

    fun isValidPasswords(password: String, passwordConfirmation: String) {
        viewState.showButtonStateOfPasswordRecoveryVerification(
            fieldsValidator.isEquals(password, passwordConfirmation)
        )
    }

    fun startTimerResendCode() {
        var countSeconds = AppConfigs.TIMEOUT_TO_RESEND
        countDownTimer = Observable.interval(1000, TimeUnit.MILLISECONDS)
            .map { countSeconds-- }
            .observeOn(AndroidSchedulers.mainThread())
            .await(withProgress = false) { seconds ->
                if (seconds <= 0) {
                    countDownTimer?.dispose()
                }
                viewState.showSecondsToResend(seconds)
            }
    }

    fun exit() {
        router.exit()
    }

    fun toAuthorization() {
        router.navigateTo(Screens.Login())
    }

    fun recoveryPassword(email: String) {
        authInteractor.sendLetterToRecovery(email).await {
            viewState.showTimer()
        }
    }

    fun checkKey(key: String) {
        authInteractor.checkTokenToRecovery(key)
            .await {
                sessionAttrs = it
                viewState.showInputNewPassword()
            }
    }

    fun changePassword(password: String) {
        sessionAttrs?.let { sessionAttrs ->
            authInteractor.setNewPassword(password, sessionAttrs)
                .await {
                    viewState.startDialogPasswordRecoverySuccessful()
                    Handler(Looper.getMainLooper()).postDelayed({
                        router.newRootScreen(Screens.Login())
                    }, 2000L)
                }
        }
    }

}