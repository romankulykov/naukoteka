package medved.studio.pharmix.ui

import medved.studio.domain.repositories.auth.models.SocialType

object AppConfigs {

    fun authSocialUrl(socialType: SocialType, redirectUrl: String) =
        "https://stage.naukotheka.ru/api/a/oauth2/authorize/nkt?idp=${socialType.raw}&redirect_uri=${redirectUrl}"

    const val SOCIAL_LOGIN_ENDPOINT = "login-actions/social-login"

}