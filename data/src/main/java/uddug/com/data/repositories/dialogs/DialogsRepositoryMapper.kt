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
            dialogImage = mapImageToDomain(dialogImage),
            lastMessage = mapLastMessageChat(lastMessage),
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

    private fun mapUserChatToDomain(dto: UserChatPreviewDto?) = dto?.run {
        UserChatPreview(
            image = mapImageToDomain(image),
            userId = userId,
            isAdmin = isAdmin,
            fullName = fullName,
            nickname = nickname
        )
    }

    private fun mapImageToDomain(dto: ImageChatPreviewDto?) = dto?.run {
        ImageChatPreview(
            id = id,
            path = path,
            fileType = fileType,
            filename = filename,
            contentType = contentType
        )
    }

    private fun mapLastMessageChat(dto: LastMessageChatPreviewDto) = dto.run {
        LastMessageChatPreview(
            id = id,
            text = text,
            type = type,
            files = files,
            ownerId = ownerId,
            createdAt = createdAt
        )
    }


}