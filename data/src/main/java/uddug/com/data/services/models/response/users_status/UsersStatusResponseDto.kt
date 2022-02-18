package uddug.com.data.services.models.response.users_status

import com.google.gson.annotations.SerializedName
import uddug.com.data.services.models.response.dialogs.StatusDto


data class UsersStatusResponseDto(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("status")
    val status: StatusDto
)
