package medved.studio.pharmix.presentation.chats

import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class ChatsPresenter(val router: AppRouter) : BasePresenterImpl<ChatsView>() {

    fun exit() {
        router.exit()
    }
}