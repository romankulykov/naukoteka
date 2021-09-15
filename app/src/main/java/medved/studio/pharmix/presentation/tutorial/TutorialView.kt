package medved.studio.pharmix.presentation.tutorial

import medved.studio.domain.entities.TutorialEntity
import medved.studio.pharmix.global.views.InformativeView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TutorialView : MvpView, InformativeView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showTutorialInformation(resources: List<TutorialEntity>)
}