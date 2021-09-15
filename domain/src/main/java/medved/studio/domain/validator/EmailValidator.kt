package medved.studio.domain.validator

interface EmailValidator {
    fun isValidEmail(email : String): Boolean
}