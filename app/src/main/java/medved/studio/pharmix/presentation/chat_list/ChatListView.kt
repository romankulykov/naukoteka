package medved.studio.pharmix.presentation.chat_list

import medved.studio.domain.entities.ChatListEntity
import medved.studio.pharmix.global.views.InformativeView
import medved.studio.pharmix.global.views.LoadingView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatListView : MvpView, LoadingView, InformativeView {

    fun getChats(chatListEntity: ChatListEntity)

}