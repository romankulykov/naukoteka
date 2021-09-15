package medved.studio.pharmix.global.views

import medved.studio.pharmix.R
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
    fun showIconableMessage(message: String?, drawableResId: Int = R.drawable.ic_tick_on)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showIconableMessage(message: Int?, drawableResId: Int = R.drawable.ic_tick_on)
}