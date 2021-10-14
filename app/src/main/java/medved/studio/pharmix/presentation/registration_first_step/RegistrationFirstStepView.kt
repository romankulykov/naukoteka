package medved.studio.pharmix.presentation.registration_first_step

import medved.studio.domain.repositories.auth.models.SocialType
import medved.studio.pharmix.global.base.SocialLoginView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RegistrationFirstStepView : SocialLoginView {
    fun showButtonState(isEnabled: Boolean)
    fun showSocialTypes(socialTypes: List<SocialType>)
}