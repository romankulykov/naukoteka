package uddug.com.naukoteka.presentation.auth_flow.login

import uddug.com.domain.repositories.auth.models.SocialType
import uddug.com.naukoteka.global.base.SocialLoginView
import uddug.com.naukoteka.ui.fragments.auth_flow.login.CustomLanguage
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType


@StateStrategyType(AddToEndSingleStrategy::class)
interface LoginView : SocialLoginView {
    fun showButtonState(isEnabled: Boolean)
    fun showSuccessLogin()
    fun showSocialTypes(socialTypes: List<SocialType>)
    fun showErrorCredentials()
    fun showLanguages(locales: List<CustomLanguage>)
}