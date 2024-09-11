package uddug.com.domain.repositories.dialogs.models

data class SearchDialogs(
    val id: Int,
    val dialogName: String?,
    val fullName: String,
    val nickname: String?,
    val image: AttachmentChatPreview?
)