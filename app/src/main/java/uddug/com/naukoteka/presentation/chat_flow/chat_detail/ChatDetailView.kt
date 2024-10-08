package uddug.com.naukoteka.presentation.chat_flow.chat_detail

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.SearchMessagesInDialogs
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.DropInChatEvent

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatDetailView : MvpView, LoadingView, InformativeView {

    fun toggleSelectionMode(messagesSelected: Boolean, message: ChatMessage? = null)
    fun toggleMessage(
        message: ChatMessage,
        selectedMessages: HashSet<ChatMessage>,
        isSelected: Boolean
    )

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMessages(messages: List<ChatMessage>, needToClear: Boolean = false)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun addToStart(message: ChatMessage)
    fun initChat(chat: ChatPreview)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun initChatAdapter()
    fun clearFiles()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun pickFiles()
    fun onMessageClick(something: DropInChatEvent.ClickEvent)

    fun toggleSearchMode(isSearchMode: Boolean)
    fun showFoundedMessages(
        messagePosition: Int,
        messageToShow: ChatMessage?,
        foundedMessageIdToMessage: MutableMap<SearchMessagesInDialogs, ChatMessage>
    )

}