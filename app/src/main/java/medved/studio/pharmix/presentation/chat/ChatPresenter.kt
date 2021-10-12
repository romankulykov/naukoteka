package medved.studio.pharmix.presentation.chat

import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class ChatPresenter(val router: AppRouter) : BasePresenterImpl<ChatView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun exit() {
        router.exit()
    }
}