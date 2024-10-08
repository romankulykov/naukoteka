package uddug.com.naukoteka.presentation.chat_flow.chats

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.interactors.dialogs.DialogsInteractor
import uddug.com.domain.interactors.messages.MessagesInteractor
import uddug.com.domain.interactors.users_search.UsersSearchInteractor
import uddug.com.domain.repositories.dialogs.models.ChatMessageUI
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.naukoteka.data.DialogLongPressMenu
import uddug.com.naukoteka.global.SwipeRefreshLoading
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
class ChatsPresenter(
    private val dialogsInteractor: DialogsInteractor,
    private val usersSearchInteractor: UsersSearchInteractor,
    private val messagesInteractor: MessagesInteractor,
    val router: AppRouter
) : BasePresenterImpl<ChatsView>() {

    var isFirstLaunched = true

    private val pageLimit: Int = 10
    private var loadMore = false
    private var whoOnline = mutableSetOf<String>()

    override fun attachView(view: ChatsView?) {
        getDialogs(isFirstLaunched, onlyUpdateExist = !isFirstLaunched)
        super.attachView(view)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        isFirstLaunched = false
    }

    fun getDialogs(
        withProgress: Boolean,
        lastDialogId: Int? = null,
        onlyUpdateExist: Boolean = false
    ) {
        dialogsInteractor.getDialogs(pageLimit, lastDialogId)
            .await(
                withProgress = withProgress,
                loaderType = SwipeRefreshLoading
            ) {
                loadMore = it.dialogs.size == pageLimit
                loadUsersStatuses(it.dialogs)
                viewState.showChats(it, lastDialogId == null, loadMore, onlyUpdateExist)
            }
    }

    fun loadUsersStatuses(dialogs: List<ChatPreview>) {
        usersSearchInteractor.getUsersChatStatuses(dialogs)
            .await(withProgress = false) {
                whoOnline.addAll(it)
                viewState.updateOnlineStatus(it)
            }
    }

    fun onChatClick(chat: ChatMessageUI) {
        if (chat is ChatPreview) {
            chat.interlocutor?.isOnline = whoOnline.contains(chat.interlocutor?.userId)
            router.navigateTo(Screens.ChatDetail(chat))
        }
    }

    fun exit() {
        router.exit()
    }

    fun deleteDialog(dialog: ChatPreview) {
        dialogsInteractor.clearDialog(dialog.dialogId)
            .await(withProgress = false) {
                viewState.deleteChat(dialog)
            }
    }

    fun showCreateChat() {
        router.navigateTo(Screens.ChatAddContact())
    }

    fun togglePin(chatListEntity: ChatPreview) {
        dialogsInteractor.togglePin(chatListEntity)
            .await { getDialogs(true) }
    }

    fun toggleNotifications(chatListEntity: ChatPreview) {
        dialogsInteractor.toggleNotifications(chatListEntity)
            .await { viewState.updateDialog(it) }
    }

    fun readMessages(dialogId: Int) {
        dialogsInteractor.readDialogMessages(dialogId)
            .await { viewState.updateDialog(it) }
    }

    fun getDialogLongPressMenu(chatPreview: ChatPreview): ArrayList<DialogLongPressMenu> {
        val items = ArrayList<DialogLongPressMenu>().apply {
            if (chatPreview.isPinned) {
                add(DialogLongPressMenu.UNPIN_CHAT)
            } else {
                add(DialogLongPressMenu.PIN_CHAT)
            }
            add(DialogLongPressMenu.HIDE_CHAT)
            if (chatPreview.isNotificationEnabled) {
                add(DialogLongPressMenu.DISABLE_NOTIFICATIONS)
            } else {
                add(DialogLongPressMenu.ENABLE_NOTIFICATIONS)
            }
            add(DialogLongPressMenu.CLEAR_THE_HISTORY)
            add(DialogLongPressMenu.BLOCK)
        }
        return items
    }

}