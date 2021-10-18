package uddug.com.naukoteka.presentation.chats

import uddug.com.domain.entities.ChatListEntity
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatsView : MvpView, LoadingView, InformativeView {

    fun getChats(chatListEntity: ChatListEntity)

}