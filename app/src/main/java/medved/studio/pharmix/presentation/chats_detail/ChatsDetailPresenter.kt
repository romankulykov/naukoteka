package medved.studio.pharmix.presentation.chats_detail

import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class ChatsDetailPresenter(val router: AppRouter) : BasePresenterImpl<ChatsDetailView>() {

    fun exit() {
        router.exit()
    }
}