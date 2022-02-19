package uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.search_in_chat

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.SearchDialogs
import uddug.com.domain.repositories.dialogs.models.SearchMessagesInDialogs
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView

@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchInChatsAndMessagesView : MvpView, LoadingView, InformativeView {

    fun showDialogs(dialogs: List<SearchDialogs>)
    fun showMessages(messages: List<SearchMessagesInDialogs>)

}