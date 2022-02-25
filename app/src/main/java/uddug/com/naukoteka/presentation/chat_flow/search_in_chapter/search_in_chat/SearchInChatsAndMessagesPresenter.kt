package uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.search_in_chat

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.interactors.dialogs.DialogsInteractor
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.naukoteka.di.modules.SearchInChatModule
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
class SearchInChatsAndMessagesPresenter(
    private val searchInChat: SearchInChatModule.SearchInChatParams,
    private val dialogsInteractor: DialogsInteractor,
    private val router: AppRouter,
) : BasePresenterImpl<SearchInChatsAndMessagesView>() {

    private val searchInConcreteDialog = searchInChat.dialogId != null

    private var limitDialogs: Int = 10
    private var limitMessages: Int = 10

    fun onChatClick(dialogId: Int) {
        dialogsInteractor.getChatDetailInfo(dialogId)
            .await { chatPreview -> gotoChat(chatPreview) }
    }

    fun gotoChat(chatPreview: ChatPreview) {
        //router.newScreenChainFrom(Screens.TabsHolder(), Screens.ChatDetail(chatPreview))
        router.navigateTo(Screens.ChatDetail(chatPreview))
    }

    fun search(query: String) {
        if (searchInConcreteDialog) return
        dialogsInteractor.searchDialogs(query, limitDialogs)
            .await { viewState.showDialogs(it) }
    }

    fun searchMessages(query: String) {
        if (searchInConcreteDialog) {
            dialogsInteractor.searchMessagesInDialog(searchInChat.dialogId!!, query, limitMessages)
                .await { viewState.showMessages(it, query) }
        } else {
            dialogsInteractor.searchMessages(query, limitMessages)
                .await { viewState.showMessages(it, query) }
        }
    }


}