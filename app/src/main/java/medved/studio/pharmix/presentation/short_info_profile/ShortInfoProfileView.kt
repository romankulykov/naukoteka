package medved.studio.pharmix.presentation.short_info_profile

import medved.studio.pharmix.global.views.InformativeView
import medved.studio.pharmix.global.views.LoadingView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ShortInfoProfileView : MvpView, LoadingView, InformativeView {
    fun showButtonState(isEnabled: Boolean)
}