package uddug.com.naukoteka.presentation.chat_flow.profile

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens


@InjectConstructor
@InjectViewState
class ChatDetailInfoPresenter(val router: AppRouter) : BasePresenterImpl<ChatDetailInfoView>() {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun exit() {
        router.exit()
    }

    fun onParticipantRemove(participantsEntity: UserChatPreview) {
    }

    fun navigateToSearch(dialogId : Int) {
        router.navigateTo(Screens.SearchInChapterScreen(dialogId))
    }

}