package uddug.com.naukoteka.presentation.tabs_holder

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TabsHolderView : MvpView {

}