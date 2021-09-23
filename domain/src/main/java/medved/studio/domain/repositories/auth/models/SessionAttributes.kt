package medved.studio.domain.repositories.auth.models

data class SessionAttributes(
    val authSessionId: String,
    val clientId: String,
    val key: String,
    val tabId: String
)