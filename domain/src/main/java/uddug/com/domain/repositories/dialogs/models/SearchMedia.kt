package uddug.com.domain.repositories.dialogs.models

data class SearchMedia(
    val messageId: Int,
    val dialogId: Int,
    val category: Int,
    val mediaType: Int,
    val text: String?,
    val attachment: AttachmentChatPreview
)
