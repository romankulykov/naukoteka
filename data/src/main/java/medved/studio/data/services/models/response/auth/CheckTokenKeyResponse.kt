package medved.studio.data.services.models.response.auth
import com.google.gson.annotations.SerializedName


data class CheckTokenKeyResponse(
    @SerializedName("followUp")
    val followUp: Boolean
)