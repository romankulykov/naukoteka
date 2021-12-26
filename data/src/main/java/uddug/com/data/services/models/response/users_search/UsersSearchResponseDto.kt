package uddug.com.data.services.models.response.users_search
import com.google.gson.annotations.SerializedName
import uddug.com.data.services.models.response.dialogs.UserChatPreviewDto

data class UsersSearchResponseDto(
    @SerializedName("users")
    val users: List<UserChatPreviewDto>,
    @SerializedName("count")
    val count: Int
)