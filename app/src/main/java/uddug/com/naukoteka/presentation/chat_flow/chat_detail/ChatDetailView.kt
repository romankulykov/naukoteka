package uddug.com.naukoteka.presentation.chat_flow.chat_detail

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView

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

}