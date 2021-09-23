package medved.studio.data.services.models.response.auth
import com.google.gson.annotations.SerializedName

data class SessionAttributesResponseDto(
    @SerializedName("authSessionId")
    val authSessionId: String,
    @SerializedName("clientId")
    val clientId: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("tabId")
    val tabId: String
)