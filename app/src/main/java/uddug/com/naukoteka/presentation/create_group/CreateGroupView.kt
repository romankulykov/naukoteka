package uddug.com.naukoteka.presentation.create_group

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.repositories.participants.ParticipantsEntity
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView


@StateStrategyType(AddToEndSingleStrategy::class)
interface CreateGroupView : MvpView, LoadingView, InformativeView {

    fun showParticipants(participants: List<ParticipantsEntity>)

}