package uddug.com.domain.repositories.dialogs.models

import android.os.Parcelable
import com.stfalcon.chatkit.commons.models.IUser
import kotlinx.parcelize.Parcelize
import uddug.com.domain.ApiConstants
import uddug.com.domain.repositories.Section
import java.util.*

enum class DialogType(val type: Int) { PERSONAL(1), GROUP(2) }
enum class ContentType(val type: String? = null) {
    AUDIO("audio/mpeg"),
    VIDEO("video/mp4"),
    IMAGE("image/jpeg"),
    PDF("application/pdf"),
    OTHER
}

data class ChatsPreview(
    val dialogs: List<ChatPreview>,
    val sumUnreadMessages: Int,
    val count: Int
)

sealed class ChatMessageUI(
    open val dialogId: Int,
    open val dialogName: String,
    open val dialogImage: AttachmentChatPreview?,
    open val message: String?,
    open val messageId: Int?,
    open val messageCreatedAt: Calendar?,
)

@Parcelize
data class ChatPreview(
    override val dialogId: Int,
    override val dialogName: String,
    val dialogType: DialogType,
    var isPinned: Boolean,
    override val messageId: Int?,
    val firstMessageId: Int?,
    override val dialogImage: AttachmentChatPreview?,
    val lastMessage: LastMessageChatPreview?,
    val users: List<UserChatPreview>?,
    val unreadMessages: Int?,
    val interlocutor: UserChatPreview?,
) : Parcelable,
    ChatMessageUI(
        dialogId = dialogId,
        dialogName = dialogName,
        dialogImage = dialogImage,
        message = lastMessage?.text ?: lastMessage?.files?.first()?.filename,
        messageId = lastMessage?.id,
        messageCreatedAt = lastMessage?.createdAt,
    )

@Parcelize
data class LastMessageChatPreview(
    val id: Int,
    val text: String?,
    val type: Int,
    val files: List<AttachmentChatPreview>,
    val ownerId: String?,
    val createdAt: Calendar?
) : Parcelable

@Parcelize
data class UserChatPreview(
    val image: AttachmentChatPreview?,
    val userId: String,
    val isAdmin: Boolean,
    val fullName: String,
    val nickname: String?,
    var isOnline: Boolean = false
) : IUser, Parcelable, Section() {

    val nicknameOrFullName get() = nickname ?: fullName

    val fullNameOrNickname: String
        get() = if (fullName.isBlank()) nickname ?: "Empty name" else fullName

    override fun getId(): String {
        return userId
    }

    override fun getName(): String {
        return fullNameOrNickname
    }

    override fun getAvatar(): String? {
        return image?.fullPath
    }

    override fun sectionName(): String {
        return name.substring(0, 1)
    }

}

@Parcelize
data class AttachmentChatPreview(
    val id: String? = null,
    val path: String?,
    val fileType: Int? = null,
    val filename: String? = null,
    val contentType: ContentType? = null
) : Parcelable {
    val fullPath get() = path?.run { ApiConstants.API_ENDPOINT_FILES_BASE + this }
}

@Parcelize
data class SearchMessagesInDialogs(
    override val dialogId: Int,
    override val dialogImage: AttachmentChatPreview?,
    override val dialogName: String,
    override val message: String?,
    override val messageId: Int,
    override val messageCreatedAt: Calendar,
) : Parcelable,
    ChatMessageUI(dialogId, dialogName, dialogImage, message, messageId, messageCreatedAt)