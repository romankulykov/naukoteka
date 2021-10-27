package uddug.com.naukoteka.ui

import uddug.com.domain.entities.AdditionalOptionsEntity
import uddug.com.domain.entities.PasswordRequirementsEntity
import uddug.com.domain.repositories.auth.models.SocialType
import uddug.com.naukoteka.R

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

    fun getAdditionalOptions() = listOf(
        AdditionalOptionsEntity(R.string.search_by_conversation, R.drawable.ic_fi_search),
        AdditionalOptionsEntity(R.string.interview_materials, R.drawable.ic_fi_inbox),
        AdditionalOptionsEntity(R.string.disable_notifications, R.drawable.ic_bell),
        AdditionalOptionsEntity(R.string.clear_the_history, R.drawable.ic_clear_history),
        AdditionalOptionsEntity(R.string.add_participant, R.drawable.ic_u_plus)
    )
}