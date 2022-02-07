package uddug.com.data.repositories.dialogs

import toothpick.InjectConstructor
import uddug.com.data.services.models.response.dialogs.*
import uddug.com.domain.repositories.dialogs.models.*

@InjectConstructor
class DialogsRepositoryMapper {

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
            isPinned = isPinned == 1,
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
            lastOnline = status
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

    fun mapDialogDetailToDomain(dto: ChatMessageDto, user: UserChatPreview?) = dto.run {
        ChatMessage(
            id = id,
            text = text,
            type = if (ownerId == null) MessageType.SYSTEM else MessageType.values()
                .find { it.type == type } ?: MessageType.TEXT,
            files = files.map { mapAttachmentToDomain(it)!! },
            ownerId = ownerId,
            createdAt = createdAt,
            read = read.map { it.entries.first().run { Pair(key, value.toInt()) } },
            user = user
        )
    }


}