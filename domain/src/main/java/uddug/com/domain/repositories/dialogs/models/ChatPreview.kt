package uddug.com.domain.repositories.dialogs.models

import android.os.Parcelable
import com.stfalcon.chatkit.commons.models.IUser
import kotlinx.parcelize.Parcelize
import uddug.com.domain.ApiConstants
import uddug.com.domain.repositories.Section
import java.util.*

enum class DialogType(val type: Int) { PERSONAL(1), GROUP(2) }
enum class ContentType(val type: String) { AUDIO("audio/mpeg"), VIDEO("video/mp4"), IMAGE("image/jpeg") }

data class ChatsPreview(
    val dialogs: List<ChatPreview>,
    val sumUnreadMessages: Int,
    val count: Int
)

@Parcelize
data class ChatPreview(
    val dialogId: Int,
    val dialogName: String,
    val dialogType: DialogType,
    val messageId: Int?,
    val firstMessageId: Int?,
    val dialogImage: AttachmentChatPreview?,
    val lastMessage: LastMessageChatPreview?,
    val users: List<UserChatPreview>?,
    val unreadMessages: Int?,
    val interlocutor: UserChatPreview?,
) : Parcelable

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
    val lastOnline: Calendar?
) : IUser, Parcelable, Section() {

    val nicknameOrFullName get() = nickname ?: fullName

    override fun getId(): String {
        return userId
    }

    override fun getName(): String {
        return fullName
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
    val id: String?,
    val path: String?,
    val fileType: Int?,
    val filename: String?,
    val contentType: ContentType?
) : Parcelable {
    val fullPath get() = path?.run { ApiConstants.API_ENDPOINT_FILES_BASE + this }
}