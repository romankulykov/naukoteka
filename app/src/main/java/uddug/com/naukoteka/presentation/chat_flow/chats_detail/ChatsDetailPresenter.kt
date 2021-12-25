package uddug.com.naukoteka.presentation.chat_flow.chats_detail

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.data.cache.token.UserTokenCache
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
class ChatsDetailPresenter(
    val router: AppRouter,
    private val userTokenCache: UserTokenCache
) : BasePresenterImpl<ChatsDetailView>() {

    fun exit() {
        router.exit()
    }

    fun showCreateChat() {
        router.navigateTo(Screens.ChatAddContact())
    }

    fun showProfile() {
        router.navigateTo(Screens.Profile(isProfile = true))
    }

    fun navigateToSearchInChapter() {
        router.navigateTo(Screens.SearchInChapterScreen())
    }

    fun logout() {
        userTokenCache.clear()
        router.replaceScreen(Screens.Splash())
    }

}