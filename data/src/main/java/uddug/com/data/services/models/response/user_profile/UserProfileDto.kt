package uddug.com.data.services.models.response.user_profile
import com.google.gson.annotations.SerializedName


data class UserProfileDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("middleName")
    val middleName: Any?,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("birthDate")
    val birthDate: Any?,
    @SerializedName("phone")
    val phone: Any?,
    @SerializedName("imageUrl")
    val imageUrl: Any?,
    @SerializedName("image")
    val image: Any?,
    @SerializedName("grants")
    val grants: List<String>
)