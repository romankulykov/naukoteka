package uddug.com.naukoteka.presentation.profile

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.repositories.participants.ParticipantsEntity
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.presentation.create_group.CreateGroupPresenter


@InjectConstructor
@InjectViewState
class ChatDetailInfoPresenter(val router: AppRouter) : BasePresenterImpl<ChatDetailInfoView>() {

    private val createGroupPresenter = CreateGroupPresenter(router)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showParticipants(createGroupPresenter.listOfParticipants)
    }

    fun exit() {
        router.exit()
    }

    fun onParticipantRemove(participantsEntity: ParticipantsEntity) {
        createGroupPresenter.listOfParticipants.remove(participantsEntity)
        viewState.showParticipants(createGroupPresenter.listOfParticipants)
    }

}