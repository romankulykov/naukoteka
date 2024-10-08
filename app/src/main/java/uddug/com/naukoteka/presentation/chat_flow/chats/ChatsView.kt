package uddug.com.naukoteka.presentation.chat_flow.chats

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.ChatsPreview
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView
import uddug.com.naukoteka.global.views.SwipeRefreshView

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatsView : MvpView, LoadingView, SwipeRefreshView, InformativeView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showChats(
        chatsPreview: ChatsPreview,
        needClear: Boolean,
        loadMore: Boolean,
        onlyUpdateExist: Boolean
    )
    fun deleteChat(dialog: ChatPreview)
    fun updateDialog(dialog: ChatPreview)
    fun updateOnlineStatus(onlineUsers: List<String>)

}