package medved.studio.domain.validator

interface PhoneValidator {
    fun isValidPhone(phone: String): Boolean
}
