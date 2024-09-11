package uddug.com.data.services.models.response.dialogs

import com.google.gson.annotations.SerializedName

data class SearchMediaResponseDto(
    @SerializedName("messageId")
    val messageId: Int,
    @SerializedName("dialogId")
    val dialogId: Int,
    @SerializedName("category")
    val category: Int,
    @SerializedName("mediaType")
    val mediaType: Int,
    @SerializedName("text")
    val text: String?,
    @SerializedName("file")
    val attachment: AttachmentChatPreviewDto
)
