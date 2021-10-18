package uddug.com.naukoteka.presentation.chats_detail

import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class ChatsDetailPresenter(val router: AppRouter) : BasePresenterImpl<ChatsDetailView>() {

    fun exit() {
        router.exit()
    }
}