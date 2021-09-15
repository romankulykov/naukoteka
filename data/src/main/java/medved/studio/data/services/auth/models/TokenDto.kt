package medved.studio.data.services.auth.models
import com.google.gson.annotations.SerializedName


data class TokenResponseDto(
    @SerializedName("access")
    val access: TokenDto,
    @SerializedName("refresh")
    val refresh: TokenDto
)

data class TokenDto(
    @SerializedName("token")
    val token: String,
    @SerializedName("expiry")
    val expiry: Int
)