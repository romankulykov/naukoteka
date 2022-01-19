package uddug.com.naukoteka.presentation.chat_flow.chat_add_contact

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.interactors.dialogs.DialogsInteractor
import uddug.com.domain.interactors.users_search.UsersSearchInteractor
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
class ChatAddContactPresenter(
    val router: AppRouter,
    private val usersSearchInteractor: UsersSearchInteractor,
    private val dialogsInteractor: DialogsInteractor
) : BasePresenterImpl<ChatAddContactView>() {

    fun toCreateGroup() {
        router.navigateTo(Screens.ChatCreateGroup())
    }

    fun onQueryFilter(query: String) {
        usersSearchInteractor.usersSearch(query)
            .await { viewState.showContacts(it) }
    }

    fun onUserClick(user: UserChatPreview) {
        dialogsInteractor.createPersonalChat(user.userId)
            .await { chatPreview ->
                router.newScreenChainFrom(Screens.TabsHolder(), Screens.ChatDetail(chatPreview))
            }
    }

    fun exit() {
        router.exit()
    }
}