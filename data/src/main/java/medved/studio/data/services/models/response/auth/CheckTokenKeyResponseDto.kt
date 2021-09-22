package medved.studio.data.services.models.response.auth
import com.google.gson.annotations.SerializedName


data class CheckTokenKeyResponseDto(
    @SerializedName("followUp")
    val followUp: Boolean
)