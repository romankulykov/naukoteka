package uddug.com.naukoteka.presentation.chat_flow.chats

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.interactors.dialogs.DialogsInteractor
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
class ChatsPresenter(
    private val dialogsInteractor: DialogsInteractor,
    val router: AppRouter
) : BasePresenterImpl<ChatsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        dialogsInteractor.getDialogs()
            .await { viewState.showChats(it) }
    }

    fun onChatClick(chat: ChatPreview) {
        router.navigateTo(Screens.ChatDetail(chat))
    }

    fun exit() {
        router.exit()
    }
}