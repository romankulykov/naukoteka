package uddug.com.data.services.models.response.files
import com.google.gson.annotations.SerializedName

data class FileInfoResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("filename")
    val filename: String,
    @SerializedName("contentType")
    val contentType: String?,
    @SerializedName("fileSize")
    val fileSize: String?,
    @SerializedName("fileType")
    val fileType: String?
)