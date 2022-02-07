package uddug.com.data.services.models.response.dialogs

import com.google.gson.annotations.SerializedName
import java.util.*

data class ChatPreviewResponseDto(
    @SerializedName("dialogs")
    val dialogs: List<ChatPreviewDto>,
    @SerializedName("sumUnreadMessages")
    val sumUnreadMessages: Int,
    @SerializedName("count")
    val count: Int
)

data class ChatPreviewDto(
    @SerializedName("dialogId", alternate = ["id"])
    val dialogId: Int,
    @SerializedName("dialogName", alternate = ["name"])
    val dialogName: String?,
    @SerializedName("dialogType", alternate = ["type"])
    val dialogType: Int,
    @SerializedName("messageId")
    val messageId: Int?,
    @SerializedName("isPinned")
    val isPinned: Int?,
    @SerializedName("firstMessageId")
    val firstMessageId: Int?,
    @SerializedName("dialogImage")
    val dialogImage: AttachmentChatPreviewDto?,
    @SerializedName("lastMessage")
    val lastMessage: LastMessageChatPreviewDto?,
    @SerializedName("users")
    val users: List<UserChatPreviewDto>?,
    @SerializedName("unreadMessages")
    val unreadMessages: Int?,
    @SerializedName("interlocutor")
    val interlocutor: UserChatPreviewDto?
)

data class LastMessageChatPreviewDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String?,
    @SerializedName("type")
    val type: Int,
    @SerializedName("files")
    val files: List<AttachmentChatPreviewDto>,
    @SerializedName("ownerId")
    val ownerId: String?,
    @SerializedName("createdAt")
    val createdAt: Calendar?
)

data class UserChatPreviewDto(
    @SerializedName("image")
    val image: AttachmentChatPreviewDto,
    @SerializedName("userId", alternate = ["id"])
    val userId: String,
    @SerializedName("isAdmin")
    val isAdmin: Any?,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("status")
    val status: Calendar?
) {
    val nicknameOrFullName get() = nickname ?: fullName

    val fullNameOrNickname: String
        get() = if (fullName.isBlank()) nickname ?: "Empty name" else fullName

}

data class AttachmentChatPreviewDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("path")
    val path: String?,
    @SerializedName("fileType")
    val fileType: Int?,
    @SerializedName("filename")
    val filename: String?,
    @SerializedName("contentType")
    val contentType: String?
)