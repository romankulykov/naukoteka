package medved.studio.pharmix.presentation.password_recovery

import medved.studio.pharmix.global.views.InformativeView
import medved.studio.pharmix.global.views.LoadingView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PasswordRecoveryView : MvpView, LoadingView, InformativeView {

    fun showButtonState(isEnabled: Boolean)

    fun showSecondsToResend(seconds: Int)

}