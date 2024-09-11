package uddug.com.naukoteka.presentation.chat_flow.create_group

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.interactors.dialogs.DialogsInteractor
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens


@InjectConstructor
@InjectViewState
class CreateGroupPresenter(
    val router: AppRouter,
    private val dialogsInteractor: DialogsInteractor
) : BasePresenterImpl<CreateGroupView>() {

    private var users: ArrayList<UserChatPreview>? = null
    private var groupName: String = ""

    fun setUsers(users: ArrayList<UserChatPreview>) {
        this.users = users
        viewState.showParticipants(users)
    }

    fun onParticipantRemove(participantsEntity: UserChatPreview) {
        users?.remove(participantsEntity)
        users?.let { viewState.showParticipants(it) }
        handleEnableButton()
    }

    fun exit() {
        router.exit()
    }

    fun changeGroupName(groupName: String) {
        this.groupName = groupName
        handleEnableButton()
    }

    private fun handleEnableButton() {
        viewState.handleEnableCreate(groupName.isNotBlank() && !users.isNullOrEmpty())
    }

    fun createGroup() {
        dialogsInteractor.createDialog(groupName, users!!.map { it.userId })
            .await { chatPreview ->
                router.newScreenChainFrom(Screens.TabsHolder(), Screens.ChatDetail(chatPreview))
            }
    }


}