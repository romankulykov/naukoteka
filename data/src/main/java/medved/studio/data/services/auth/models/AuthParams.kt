package medved.studio.data.services.auth.models

import com.google.gson.annotations.SerializedName

data class AuthPhoneEmailParams(
    @SerializedName("email_or_phone_number")
    val emailOrPhone: String,
    @SerializedName("password")
    val password: String,
)

data class SendCodeToEmailParams(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("verification_id")
    val verificationId: String? = null
)

data class SendCodeToPhoneParams(
    @SerializedName("phone_number")
    val phone: String? = null,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("verification_id")
    val verificationId: String? = null
)

data class SocialAuthParams(@SerializedName("access_token") val token: String)