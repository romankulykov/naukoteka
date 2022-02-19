package uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.search_in_chat

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.interactors.dialogs.DialogsInteractor
import uddug.com.domain.repositories.dialogs.models.ChatMessageUI
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
class SearchInChatsAndMessagesPresenter(
    private val dialogsInteractor: DialogsInteractor,
    private val router: AppRouter,
) : BasePresenterImpl<SearchInChatsAndMessagesView>() {

    private var limitDialogs: Int = 10
    private var limitMessages: Int = 10

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun onChatClick(dialogId : Int) {
        dialogsInteractor.getChatDetailInfo(dialogId)
            .await { chatPreview -> gotoChat(chatPreview) }
    }
    fun gotoChat(chatPreview: ChatPreview) {
        router.newScreenChainFrom(Screens.TabsHolder(), Screens.ChatDetail(chatPreview))
    }

    fun search(query: String) {
        dialogsInteractor.searchDialogs(query, limitDialogs)
            .await { viewState.showDialogs(it) }
    }

    fun searchMessages(query: String) {
        dialogsInteractor.searchMessagesInDialog(query, limitMessages)
            .await { viewState.showMessages(it) }
    }


}