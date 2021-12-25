package uddug.com.data.services.models.response.dialogs

import com.google.gson.annotations.SerializedName

data class ChatPreviewResponseDto(
    @SerializedName("dialogs")
    val dialogs: List<ChatPreviewDto>,
    @SerializedName("sumUnreadMessages")
    val sumUnreadMessages: Int,
    @SerializedName("count")
    val count: Int
)

data class ChatPreviewDto(
    @SerializedName("dialogId")
    val dialogId: Int,
    @SerializedName("dialogName")
    val dialogName: String?,
    @SerializedName("dialogType")
    val dialogType: Int,
    @SerializedName("messageId")
    val messageId: Int,
    @SerializedName("dialogImage")
    val dialogImage: ImageChatPreviewDto?,
    @SerializedName("lastMessage")
    val lastMessage: LastMessageChatPreviewDto,
    @SerializedName("users")
    val users: List<UserChatPreviewDto>,
    @SerializedName("unreadMessages")
    val unreadMessages: Int,
    @SerializedName("interlocutor")
    val interlocutor: UserChatPreviewDto?
)

data class LastMessageChatPreviewDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("files")
    val files: List<Any>,
    @SerializedName("ownerId")
    val ownerId: Any?,
    @SerializedName("createdAt")
    val createdAt: String
)

data class UserChatPreviewDto(
    @SerializedName("image")
    val image: ImageChatPreviewDto,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("isAdmin")
    val isAdmin: Int,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("nickname")
    val nickname: String?
) {
    val nicknameOrFullName get() = nickname ?: fullName
}

data class ImageChatPreviewDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("fileType")
    val fileType: Int,
    @SerializedName("filename")
    val filename: String,
    @SerializedName("contentType")
    val contentType: String?
)