package medved.studio.pharmix.presentation.password_recovery

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import medved.studio.data.validator.FieldsValidator
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import moxy.InjectViewState
import toothpick.InjectConstructor
import java.util.concurrent.TimeUnit

@InjectConstructor
@InjectViewState
class PasswordRecoveryPresenter(
    private val fieldsValidator: FieldsValidator,
    private val context: Context,
    val router: AppRouter
) : BasePresenterImpl<PasswordRecoveryView>()  {

    private var countDownTimer: Disposable? = null

    override fun attachView(view: PasswordRecoveryView?) {
        super.attachView(view)
    }

    fun isValidField(email: String) {
        viewState.showButtonState(
            fieldsValidator.isValidEmail(email)
        )
    }

    fun isValidFields(password: String, passwordConfirmation: String) {
        viewState.showButtonStateOfPasswordRecoveryVerification(
            fieldsValidator.isNotEmpty(password) &&
                    fieldsValidator.isNotEmpty(passwordConfirmation)
        )
    }

    fun startTimerResendCode() {
        var countSeconds = 120
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
}