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

    private fun mapChatPreviewToDomain(dto: ChatPreviewDto) = dto.run {
        val dialogType = DialogType.values().find { it.type == dialogType } ?: DialogType.PERSONAL
        ChatPreview(
            dialogId = dialogId,
            dialogName = fillChatName(dto, dialogType),
            dialogType = dialogType,
            messageId = messageId,
            dialogImage = mapAttachmentToDomain(dialogImage),
            lastMessage = mapLastMessageChatDomain(lastMessage),
            users = users.map { mapUserChatToDomain(it)!! },
            unreadMessages = unreadMessages,
            interlocutor = mapUserChatToDomain(interlocutor)
        )
    }

    private fun fillChatName(chatPreviewDto: ChatPreviewDto, dialogType: DialogType): String {
        return when (dialogType) {
            DialogType.PERSONAL -> {
                chatPreviewDto.interlocutor!!.nicknameOrFullName
            }
            DialogType.GROUP -> {
                chatPreviewDto.users.joinToString(", ") { it.nicknameOrFullName }
            }
        }
    }

    fun mapUserChatToDomain(dto: UserChatPreviewDto?) = dto?.run {
        UserChatPreview(
            image = mapAttachmentToDomain(image),
            userId = userId,
            isAdmin = isAdmin == 1,
            fullName = fullName,
            nickname = nickname
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

    private fun mapLastMessageChatDomain(dto: LastMessageChatPreviewDto) = dto.run {
        LastMessageChatPreview(
            id = id,
            text = text,
            type = type,
            files = files.map { mapAttachmentToDomain(it)!! },
            ownerId = ownerId,
            createdAt = createdAt
        )
    }

    fun mapDialogDetailToDomain(dto: ChatMessageDto, chatPreview: ChatPreview) = dto.run {
        ChatMessage(
            id = id,
            text = text,
            type = MessageType.values().find { it.type == type } ?: MessageType.TEXT,
            files = files.map { mapAttachmentToDomain(it)!! },
            ownerId = ownerId,
            createdAt = createdAt,
            read = read.map { it.entries.first().run { Pair(key, value.toInt()) } },
            user = chatPreview.users.find { it.userId == ownerId }
        )
    }


}