package uddug.com.naukoteka.presentation.chats

import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.entities.ChatListEntity
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
class ChatsPresenter(val router: AppRouter) : BasePresenterImpl<ChatsView>() {

    fun onChatClick(chatListEntity: ChatListEntity) {
        router.navigateTo(Screens.ChatDetail())
    }

    fun exit() {
        router.exit()
    }
}