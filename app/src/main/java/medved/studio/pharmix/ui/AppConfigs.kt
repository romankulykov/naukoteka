package medved.studio.pharmix.ui

import medved.studio.domain.entities.PasswordRequirementsEntity
import medved.studio.domain.repositories.auth.models.SocialType
import medved.studio.pharmix.R

object AppConfigs {

    fun authSocialUrl(socialType: SocialType, redirectUrl: String) =
        "https://stage.naukotheka.ru/api/a/oauth2/authorize/nkt?idp=${socialType.raw}&redirect_uri=${redirectUrl}"

    const val SOCIAL_LOGIN_ENDPOINT = "login-actions/social-login"
    const val TIMEOUT_TO_RESEND = 10

    fun getPasswordRequirements() = listOf(
        PasswordRequirementsEntity(R.string.dialog_password_requirements_count_of_symbols),
        PasswordRequirementsEntity(R.string.dialog_password_requirements_capital_letters),
        PasswordRequirementsEntity(R.string.dialog_password_requirements_digits),
        PasswordRequirementsEntity(R.string.dialog_password_requirements_symbols)
    )

}