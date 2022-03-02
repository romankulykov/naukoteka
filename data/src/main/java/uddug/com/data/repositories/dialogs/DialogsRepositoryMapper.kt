package uddug.com.data.repositories.dialogs

import toothpick.InjectConstructor
import uddug.com.data.cache.user_uuid.UserUUIDCache
import uddug.com.data.services.models.response.dialogs.*
import uddug.com.domain.repositories.dialogs.models.*
import java.util.*

@InjectConstructor
class DialogsRepositoryMapper(
    private val userUUID: UserUUIDCache,
) {

    fun mapChatsPreviewToDomain(dto: ChatPreviewResponseDto) = dto.run {
        ChatsPreview(
            dialogs = dialogs.map { mapChatPreviewToDomain(it) },
            sumUnreadMessages = sumUnreadMessages,
            count = count
        )
    }

    fun mapChatPreviewToDomain(dto: ChatPreviewDto) = dto.run {
        val dialogType = DialogType.values().find { it.type == dialogType } ?: DialogType.PERSONAL
        ChatPreview(
            dialogId = dialogId,
            dialogName = fillChatName(this, dialogType),
            dialogType = dialogType,
            isPinned = (isPinned as? Boolean)?.run { this } ?: (isPinned as? Int?)?.run { this == 1 } ?: (isPinned as? Double?)?.run { this == 1.0 } == true,
            isNotificationEnabled = notificationsDisable?.run { this } ?: true,
            messageId = messageId,
            firstMessageId = firstMessageId,
            dialogImage = mapAttachmentToDomain(dialogImage ?: interlocutor?.image),
            lastMessage = mapLastMessageChatDomain(lastMessage),
            users = users?.map { mapUserChatToDomain(it)!! },
            unreadMessages = unreadMessages,
            interlocutor = mapUserChatToDomain(interlocutor),
        )
    }

    private fun fillChatName(chatPreviewDto: ChatPreviewDto, dialogType: DialogType): String {
        return when (dialogType) {
            DialogType.PERSONAL -> {
                chatPreviewDto.interlocutor?.fullNameOrNickname.toString()
            }
            DialogType.GROUP -> {
                if (chatPreviewDto.dialogName.isNullOrBlank()) {
                    chatPreviewDto.users!!.joinToString(", ") { it.nicknameOrFullName }
                } else {
                    chatPreviewDto.dialogName
                }
            }
        }
    }

    fun mapUserChatToDomain(dto: UserChatPreviewDto?) = dto?.run {
        UserChatPreview(
            image = mapAttachmentToDomain(image),
            userId = userId,
            isAdmin = isAdmin == 1 || isAdmin == true,
            fullName = fullName,
            nickname = nickname,
        )
    }

    private fun mapAttachmentToDomain(dto: AttachmentChatPreviewDto?) = dto?.run {
        AttachmentChatPreview(
            id = id,
            path = path,
            fileType = fileType,
            filename = filename,
            contentType = ContentType.values().find { it.type == contentType }
        )
    }

    private fun mapLastMessageChatDomain(dto: LastMessageChatPreviewDto?) = dto?.run {
        LastMessageChatPreview(
            id = id,
            text = text,
            type = type,
            files = files.map { mapAttachmentToDomain(it)!! },
            ownerId = ownerId,
            createdAt = createdAt
        )
    }

    fun mapSearchDialog(dto: SearchDialogResponseDto) = dto.run {
        SearchDialogs(
            id = id,
            dialogName = dialogName,
            fullName = fullName,
            nickname = nickname,
            image = mapAttachmentToDomain(image)
        )
    }

    fun mapDialogDetailToDomain(dto: ChatMessageDto, chatPreview: ChatPreview?) = dto.run {
        ChatMessage(
            id = id,
            text = text,
            type = if (ownerId == null) MessageType.SYSTEM else MessageType.values()
                .find { it.type == type } ?: MessageType.TEXT,
            files = files.map { mapAttachmentToDomain(it)!! },
            ownerId = ownerId,
            createdAt = createdAt,
            read = read.map { it.entries.first().run { Pair(key, value.toInt()) } },
            user = getUser(this, chatPreview),
            chatPreview = chatPreview,
        )
    }

    fun getUser(chatMessage: ChatMessageDto, chatPreview: ChatPreview?): UserChatPreview? {
        val user = if (chatMessage.ownerId == null) {
            // messages with null ownerId means that it is system message
            UserChatPreview(null, userUUID.requireEntity, false, "", "")
        } else if (chatPreview?.dialogType == DialogType.GROUP) {
            chatPreview.users?.find { it.userId == chatMessage.ownerId }
        } else {
            if (userUUID.entity == chatMessage.ownerId) {
                UserChatPreview(null, userUUID.requireEntity, false, "", "")
            } else {
                chatPreview?.interlocutor
            }
        }
        return user
    }

    fun mapMessageToSearchMessageDomain(
        messageDto: ChatMessageDto,
        chatDto: ChatPreviewDto,
    ): SearchMessagesInDialogs {
        val dialogType =
            DialogType.values().find { it.type == chatDto.dialogType } ?: DialogType.PERSONAL
        return SearchMessagesInDialogs(
            dialogId = chatDto.dialogId,
            dialogImage = mapAttachmentToDomain(chatDto.users?.find { it.userId == messageDto.ownerId }?.image),
            dialogName = fillChatName(chatDto, dialogType),
            message = messageDto.text,
            messageId = messageDto.id,
            messageCreatedAt = Calendar.getInstance()
        )

    }

    fun mapSearchMediaContentToDomain(dto: SearchMediaResponseDto) =
        dto.run {
            SearchMedia(
                messageId = messageId,
                dialogId = dialogId,
                category = category,
                mediaType = mediaType,
                text = text,
                attachment = mapAttachmentToDomain(attachment)!!
            )
        }


}