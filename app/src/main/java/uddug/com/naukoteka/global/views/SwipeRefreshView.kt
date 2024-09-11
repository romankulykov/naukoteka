package uddug.com.naukoteka.global.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SwipeRefreshView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRefreshLoading(show: Boolean)

}