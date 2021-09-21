package medved.studio.pharmix.presentation.registration_first_step

import medved.studio.domain.repositories.auth.models.SocialType
import medved.studio.pharmix.global.views.InformativeView
import medved.studio.pharmix.global.views.LoadingView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RegistrationFirstStepView : MvpView, LoadingView, InformativeView {
    fun showButtonState(isEnabled: Boolean)
    fun showSocialTypes(socialTypes: List<SocialType>)
}