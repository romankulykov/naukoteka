package medved.studio.pharmix.presentation.chat_work

import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.presentation.chat_groups.ChatGroupsView
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class ChatWorkPresenter(val router: AppRouter) : BasePresenterImpl<ChatWorkView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun exit() {
        router.exit()
    }
}