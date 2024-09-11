package uddug.com.naukoteka.presentation.chat_flow.chat_select_contact

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.interactors.users_search.UsersSearchInteractor
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
class ChatSelectContactPresenter(
    private val router: AppRouter,
    private val usersSearchInteractor: UsersSearchInteractor
) :
    BasePresenterImpl<ChatSelectContactView>() {

    private val selectedChatList: MutableList<UserChatPreview> = mutableListOf()

    fun toCreateGroup() {
        router.navigateTo(Screens.CreateGroup(selectedChatList))
    }

    fun removeSelectedContacts(chatContact: UserChatPreview) {
        if (selectedChatList.contains(chatContact)) {
            selectedChatList.remove(chatContact)
            viewState.showSelectedContacts(selectedChatList)
        }
    }

    fun onQueryFilter(query: String) {
        usersSearchInteractor.usersSearch(query)
            .await { viewState.showContacts(it) }
    }

    fun onContactClicked(chatContact: UserChatPreview) {
        addSelectedContacts(chatContact)
    }

    private fun addSelectedContacts(chatContact: UserChatPreview) {
        if (!selectedChatList.contains(chatContact)) {
            selectedChatList.add(chatContact)
            viewState.showSelectedContacts(selectedChatList, true)
        } else {
            selectedChatList.remove(chatContact)
            viewState.showSelectedContacts(selectedChatList)
        }

    }

    fun exit() {
        router.exit()
    }
}