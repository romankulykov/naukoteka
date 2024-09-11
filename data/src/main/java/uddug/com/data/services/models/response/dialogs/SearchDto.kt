package uddug.com.data.services.models.response.dialogs

import com.google.gson.annotations.SerializedName
import java.util.*

data class SearchDialogResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("dialogName")
    val dialogName: String?,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("image")
    val image: AttachmentChatPreviewDto?
)

data class SearchChatMessageDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String?,
    @SerializedName("type")
    val type: Int,
    @SerializedName("files")
    val files: Any,
    @SerializedName("ownerId")
    val ownerId: String?,
    @SerializedName("createdAt")
    val createdAt: Calendar?
) {
    fun toChatMessage() = ChatMessageDto(
        id = id,
        text = text,
        type = type,
        files = emptyList(),
        ownerId = ownerId,
        createdAt = createdAt ?: Calendar.getInstance(),
        arrayListOf()
    )
}