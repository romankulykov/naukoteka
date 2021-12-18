package uddug.com.naukoteka.presentation.chat_flow.create_group

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.repositories.participants.ParticipantsEntity
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter


@InjectConstructor
@InjectViewState
class CreateGroupPresenter(
    val router: AppRouter
) : BasePresenterImpl<CreateGroupView>() {

    val listOfParticipants = mutableListOf(
        ParticipantsEntity("Михаил Маркин", "Онлайн"),
        ParticipantsEntity("Александр Ивановский", "был(а) в этом месяце"),
        ParticipantsEntity("Vasiliy Chernenko", "был(а) недавно"),
    )

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showParticipants(listOfParticipants)
    }

    fun onParticipantRemove(participantsEntity: ParticipantsEntity) {
        listOfParticipants.remove(participantsEntity)
        viewState.showParticipants(listOfParticipants)
    }

    fun exit() {
        router.exit()
    }

}