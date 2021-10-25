package uddug.com.naukoteka.presentation.chat_detail

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter

@InjectConstructor
@InjectViewState
open class ChatDetailPresenter(val router: AppRouter) :
    BasePresenterImpl<ChatDetailView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun exit() {
        router.exit()
    }
}