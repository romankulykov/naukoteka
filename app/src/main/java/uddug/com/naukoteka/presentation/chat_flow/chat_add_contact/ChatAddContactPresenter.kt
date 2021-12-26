package uddug.com.naukoteka.presentation.chat_flow.chat_add_contact

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.interactors.users_search.UsersSearchInteractor
import uddug.com.domain.repositories.ChatContact
import uddug.com.domain.repositories.Header
import uddug.com.domain.repositories.Section
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
class ChatAddContactPresenter(
    val router: AppRouter,
    private val usersSearchInteractor: UsersSearchInteractor
) : BasePresenterImpl<ChatAddContactView>() {

    fun toCreateGroup() {
        router.navigateTo(Screens.ChatCreateGroup())
    }

    fun onQueryFilter(query: String) {
        usersSearchInteractor.usersSearch(query)
            .await { viewState.showContacts(it) }
    }

    fun exit() {
        router.exit()
    }
}