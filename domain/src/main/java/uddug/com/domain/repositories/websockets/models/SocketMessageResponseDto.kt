package uddug.com.domain.repositories.websockets.models
import com.google.gson.annotations.SerializedName

data class SocketMessageResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("dialog")
    val dialog: Int,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("cType")
    val cType: Int
)
