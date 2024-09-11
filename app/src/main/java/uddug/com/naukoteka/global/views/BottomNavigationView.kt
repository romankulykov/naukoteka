package uddug.com.naukoteka.global.views

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.naukoteka.navigation.Screens

interface BottomNavigationView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun changeBadgeCountChat(count: Int)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun bottomMenuClick(bottomMenuView: Screens.BottomNavigationTab)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun bottomMenuVisibility(isVisible: Boolean)
}