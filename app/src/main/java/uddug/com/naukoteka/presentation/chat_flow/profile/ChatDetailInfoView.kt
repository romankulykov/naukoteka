package uddug.com.naukoteka.presentation.chat_flow.profile

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView


@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatDetailInfoView : MvpView, LoadingView, InformativeView {

    fun showParticipants(participants: List<UserChatPreview>)

}