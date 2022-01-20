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

    var isFirstLaunched = true

    override fun attachView(view: ChatsView?) {
        super.attachView(view)
        if (!isFirstLaunched) {
            getDialogs(withProgress = false, withRefresh = false)
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getDialogs()
        isFirstLaunched = false
    }

    fun getDialogs(withProgress: Boolean = false, withRefresh: Boolean = true) {
        dialogsInteractor.getDialogs()
            .await(
                withProgress = withProgress,
                withRefreshProgress = withRefresh
            ) { viewState.showChats(it) }
    }

    fun onChatClick(chat: ChatPreview) {
        router.navigateTo(Screens.ChatDetail(chat))
    }

    fun exit() {
        router.exit()
    }

    fun deleteDialog(dialog: ChatPreview) {
        dialogsInteractor.deletePersonalDialog(dialog.dialogId)
            .await(withProgress = false) {
                viewState.deleteChat(dialog)
            }
    }
}