package medved.studio.pharmix.presentation.registration_third_step

import medved.studio.pharmix.global.views.InformativeView
import medved.studio.pharmix.global.views.LoadingView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RegistrationThirdStepView : MvpView, LoadingView, InformativeView {
    fun showSecondsToResend(seconds: Int)
}