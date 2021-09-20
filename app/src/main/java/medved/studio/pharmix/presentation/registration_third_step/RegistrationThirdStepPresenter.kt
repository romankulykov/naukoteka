package medved.studio.pharmix.presentation.registration_third_step

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import medved.studio.data.validator.FieldsValidator
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import medved.studio.pharmix.presentation.registration_second_step.RegistrationSecondStepView
import moxy.InjectViewState
import toothpick.InjectConstructor
import java.util.concurrent.TimeUnit

@InjectConstructor
@InjectViewState
class RegistrationThirdStepPresenter(
    private val context: Context,
    val router: AppRouter
) : BasePresenterImpl<RegistrationThirdStepView>() {

    private var countDownTimer: Disposable? = null

    override fun attachView(view: RegistrationThirdStepView?) {
        super.attachView(view)
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
}