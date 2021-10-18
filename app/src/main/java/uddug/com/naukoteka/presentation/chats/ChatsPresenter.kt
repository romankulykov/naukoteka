package uddug.com.naukoteka.presentation.chats

import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class ChatsPresenter(val router: AppRouter) : BasePresenterImpl<ChatsView>() {

    fun exit() {
        router.exit()
    }
}