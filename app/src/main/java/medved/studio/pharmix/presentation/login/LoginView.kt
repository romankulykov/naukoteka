package medved.studio.pharmix.presentation.login

import medved.studio.domain.repositories.auth.models.SocialType
import medved.studio.pharmix.global.base.SocialLoginView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType


@StateStrategyType(AddToEndSingleStrategy::class)
interface LoginView : SocialLoginView {
    fun showButtonState(isEnabled: Boolean)
    fun showSuccessLogin()
    fun showSocialTypes(socialTypes: List<SocialType>)
    fun showErrorCredentials(flag: Boolean)
}