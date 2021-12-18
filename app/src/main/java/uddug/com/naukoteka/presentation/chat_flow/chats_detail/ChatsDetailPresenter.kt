package uddug.com.naukoteka.presentation.chat_flow.chats_detail

import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
class ChatsDetailPresenter(
    val router: AppRouter
) : BasePresenterImpl<ChatsDetailView>() {

    fun exit() {
        router.exit()
    }

    fun showCreateChat() {
        router.navigateTo(Screens.ChatAddContact())
    }

    fun showProfile() {
        router.navigateTo(Screens.Profile(isProfile = true))
    }
}