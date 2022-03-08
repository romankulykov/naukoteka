package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail

import android.view.View
import com.jakewharton.rxrelay2.PublishRelay
import uddug.com.domain.repositories.dialogs.models.ChatMessage

open class Payload {
    var dropInActivity: IDropInActivity? = null
    var dropInChat: PublishRelay<ChatSelectionStatus> = PublishRelay.create()
    var lastPublishedStatus: ChatSelectionStatus? = null
    var onMessageFound: PublishRelay<Int> = PublishRelay.create()

    private var selectedMessages: List<ChatMessage>? = null

    val isSelectionMode: Boolean
        get() = when (lastPublishedStatus) {
            is ChatSelectionStatus.ToggleSelectionMessage -> true
            is ChatSelectionStatus.ToggleSelectionMode -> (lastPublishedStatus as ChatSelectionStatus.ToggleSelectionMode).isSelectionState
            null -> false
        }

    fun getPossibleSelectedMessage(messageId: Int): ChatMessage? {
        return selectedMessages?.find { it.id == messageId }
    }

    fun publish(chatSelectionStatus: ChatSelectionStatus) {
        lastPublishedStatus = chatSelectionStatus
        if (chatSelectionStatus is ChatSelectionStatus.ToggleSelectionMessage) {
            selectedMessages = chatSelectionStatus.selectedMessages
        }
        dropInChat.accept(chatSelectionStatus)
    }

}

interface IDropInActivity {
    fun droppedInActivity(something: DropInChatEvent)
}

sealed class DropInChatEvent {
    data class DownloadFileEvent(val url: String) : DropInChatEvent()
    data class ClickEvent(val view: View, val height: Int, val chatMessage: ChatMessage) : DropInChatEvent()
}