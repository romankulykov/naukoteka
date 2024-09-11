package uddug.com.naukoteka.presentation.chat_flow.chats_list_detail

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatsListDetailView : MvpView, LoadingView, InformativeView {


}