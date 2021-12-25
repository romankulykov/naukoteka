package uddug.com.domain.repositories.dialogs.models

enum class DialogType(val type: Int) { PERSONAL(1), GROUP(2) }

data class ChatsPreview(
    val dialogs: List<ChatPreview>,
    val sumUnreadMessages: Int,
    val count: Int
)

data class ChatPreview(
    val dialogId: Int,
    val dialogName: String,
    val dialogType: DialogType,
    val messageId: Int,
    val dialogImage: ImageChatPreview?,
    val lastMessage: LastMessageChatPreview,
    val users: List<UserChatPreview>,
    val unreadMessages: Int,
    val interlocutor: UserChatPreview?
)

data class LastMessageChatPreview(
    val id: Int,
    val text: String,
    val type: Int,
    val files: List<Any>,
    val ownerId: Any?,
    val createdAt: String
)

data class UserChatPreview(
    val image: ImageChatPreview?,
    val userId: String,
    val isAdmin: Int,
    val fullName: String,
    val nickname: String?
)

data class ImageChatPreview(
    val id: String,
    val path: String,
    val fileType: Int,
    val filename: String,
    val contentType: String?
) {
    val fullPath get() = "https://stage.naukotheka.ru/" + path
}