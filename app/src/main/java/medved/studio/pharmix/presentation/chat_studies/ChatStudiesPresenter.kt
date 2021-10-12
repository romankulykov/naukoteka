package medved.studio.pharmix.presentation.chat_studies

import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.presentation.chat_groups.ChatGroupsView
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class ChatStudiesPresenter(val router: AppRouter) : BasePresenterImpl<ChatStudiesView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun exit() {
        router.exit()
    }
}