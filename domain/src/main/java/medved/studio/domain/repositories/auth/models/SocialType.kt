package medved.studio.domain.repositories.auth.models

enum class SocialType(val raw: String) {
    FACEBOOK("facebook"),
    MAIL_RU("mailru"),
    GOOGLE("google"),
    VK("vk"),
    OK("ok"),
}