package uddug.com.domain.repositories.dialogs.models

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import com.stfalcon.chatkit.commons.models.MessageContentType
import uddug.com.domain.ApiConstants
import java.util.*

enum class MessageType(val type: Int) { TEXT(1), SYSTEM(5) }

data class ChatMessage(
    val id: Int,
    private val text: String?,
    val type: MessageType,
    val files: List<AttachmentChatPreview>,
    val ownerId: String?,
    val createdAt: Calendar,
    val read: List<Pair<String, Int>>,
    val user: UserChatPreview?,
    val chatPreview: ChatPreview?,
) : IMessage,
    MessageContentType.Image,
    MessageContentType {

    override fun getId(): String {
        return id.toString()
    }

    override fun getText(): String? {
        return text
    }

    override fun getUser(): IUser? {
        return user
    }

    override fun getCreatedAt(): Date {
        return Date(createdAt.timeInMillis)
    }

    override fun getImageUrl(): String? {
        return files.find { it.contentType == ContentType.IMAGE }?.fullPath
    }


}