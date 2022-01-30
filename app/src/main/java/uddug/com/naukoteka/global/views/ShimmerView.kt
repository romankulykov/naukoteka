package uddug.com.naukoteka.global.views

import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ShimmerView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showShimmerLoading(show: Boolean)

}