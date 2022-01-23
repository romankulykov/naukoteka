package uddug.com.naukoteka.presentation.chat_flow.chats_list_detail

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.data.cache.token.UserTokenCache
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
class ChatsListDetailPresenter(
    private val router: AppRouter,
    private val userTokenCache: UserTokenCache
) : BasePresenterImpl<ChatsListDetailView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initView()
    }

    fun exit() {
        router.exit()
    }

    fun showCreateChat() {
        router.navigateTo(Screens.ChatAddContact())
    }

    fun navigateToSearchInChapter() {
        router.navigateTo(Screens.SearchInChapterScreen())
    }

    fun logout() {
        userTokenCache.clear()
        router.navigateTo(Screens.Splash())
    }

}