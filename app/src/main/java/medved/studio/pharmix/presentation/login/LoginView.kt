package medved.studio.pharmix.presentation.login

import medved.studio.domain.repositories.auth.models.SocialType
import medved.studio.pharmix.global.views.InformativeView
import medved.studio.pharmix.global.views.LoadingView
import medved.studio.pharmix.ui.fragments.login.CustomLanguage
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType


@StateStrategyType(AddToEndSingleStrategy::class)
interface LoginView : SocialLoginView {
    fun showButtonState(isEnabled: Boolean)
    fun showSuccessLogin()
    fun showSocialTypes(socialTypes: List<SocialType>)
    fun showErrorCredentials(flag: Boolean)
    fun showLanguages(locales: List<CustomLanguage>)
}