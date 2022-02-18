package uddug.com.data.services.models.request.user_status
import com.google.gson.annotations.SerializedName

data class UserStatusRequestDto(
    @SerializedName("users")
    val users: List<String>
)