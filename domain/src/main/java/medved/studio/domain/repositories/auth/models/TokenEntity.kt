package medved.studio.domain.repositories.auth.models

data class BunchOfTokens(val access: Token, val refresh: Token)

data class Token(val token: String, val expiry: Int)