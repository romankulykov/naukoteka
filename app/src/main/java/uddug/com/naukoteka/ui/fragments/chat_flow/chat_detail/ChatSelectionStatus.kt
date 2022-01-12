package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail

import uddug.com.domain.repositories.dialogs.models.ChatMessage

sealed class ChatSelectionStatus {

    data class ToggleSelectionMode(
        val isSelectionState: Boolean,
        val message: ChatMessage? = null
    ) : ChatSelectionStatus()

    data class ToggleSelectionMessage(
        val message: ChatMessage,
        val selectedMessages: List<ChatMessage>,
        val isSelected: Boolean
    ) : ChatSelectionStatus()

}
