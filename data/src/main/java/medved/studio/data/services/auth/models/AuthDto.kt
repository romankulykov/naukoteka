package medved.studio.data.services.auth.models
import com.google.gson.annotations.SerializedName
import medved.studio.domain.entities.ErrorCode


data class AuthDto(
    @SerializedName("error")
    val error: ErrorAuthDto,
    @SerializedName("tokens")
    val result: TokenResponseDto
)

data class ErrorAuthDto(
    @SerializedName("is_successful")
    val isSuccessful: Boolean,
    @SerializedName("code")
    val code: ErrorCode,
    @SerializedName("message")
    val message: String
)

data class VerificationDto(
    @SerializedName("verification_id")
    val verificationId: String,
    @SerializedName("expired_at")
    val expiredAt: Int,
    @SerializedName("next_attempt_available_after")
    val nextAttemptAvailableAfter: Int,
    @SerializedName("available_attempts_count")
    val availableAttemptsCount: Int,
    @SerializedName("error")
    val error: ErrorAuthDto
)