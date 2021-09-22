package medved.studio.pharmix.presentation.registration_third_step

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import medved.studio.domain.interactors.auth.AuthInteractor
import medved.studio.pharmix.di.modules.SimpleRegistration
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import moxy.InjectViewState
import toothpick.InjectConstructor
import java.util.concurrent.TimeUnit

@InjectConstructor
@InjectViewState
class RegistrationThirdStepPresenter(
    val router: AppRouter,
    private val simpleRegistration: SimpleRegistration,
    private val authInteractor: AuthInteractor,
) : BasePresenterImpl<RegistrationThirdStepView>() {

    private var countDownTimer: Disposable? = null

    override fun attachView(view: RegistrationThirdStepView?) {
        super.attachView(view)
        startTimerResendCode()
    }

    fun resendCode() {
        authInteractor.register(simpleRegistration.login!!, simpleRegistration.password!!)
            .await {
                startTimerResendCode()
            }
    }

    private fun startTimerResendCode() {
        var countSeconds = 120
        countDownTimer?.dispose()
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

    fun toFinalRegistration() {
        router.navigateTo(Screens.ShortInfoProfile())
    }
}