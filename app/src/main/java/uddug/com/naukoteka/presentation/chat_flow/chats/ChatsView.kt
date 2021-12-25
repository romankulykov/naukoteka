package uddug.com.naukoteka.presentation.chat_flow.chats

import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.repositories.dialogs.models.ChatsPreview

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatsView : MvpView, LoadingView, InformativeView {

    fun showChats(chatsPreview: ChatsPreview)

}