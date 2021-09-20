package medved.studio.pharmix.global.views

import medved.studio.pharmix.R
import medved.studio.pharmix.ui.custom.SquareToast
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface InformativeView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showInfoMessage(message: String?)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showInfoMessage(message: Int?)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showActionMessage(message: String?, action: String, invoker: () -> Unit)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showActionMessage(message: Int?, action: Int, invoker: () -> Unit)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessage(data: SquareToast.Data)
}