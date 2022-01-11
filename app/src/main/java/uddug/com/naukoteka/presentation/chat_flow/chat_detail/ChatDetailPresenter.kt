package uddug.com.naukoteka.presentation.chat_flow.chat_detail

import com.stfalcon.chatkit.commons.models.IMessage
import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.entities.AttachmentPhotoEntity
import uddug.com.domain.interactors.dialogs.DialogsInteractor
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.naukoteka.data.ChatAttachmentOption
import uddug.com.naukoteka.data.ChatOption
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
open class ChatDetailPresenter(
    val router: AppRouter,
    private val dialogsInteractor: DialogsInteractor,
) :
    BasePresenterImpl<ChatDetailView>() {

    var chatPreview: ChatPreview? = null

    var isMessagesSelected = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun getChat(chatPreview: ChatPreview) {
        this.chatPreview = chatPreview
        dialogsInteractor.getDialogMessages(chatPreview)
            .await { viewState.showMessages(it) }
    }

    fun onChatOptionClick(chatOption: ChatOption) {
        when (chatOption) {
            ChatOption.SEARCH_BY_CONVERSATION -> viewState.showDialogSearchByConversation()
            ChatOption.INTERVIEW_MATERIALS -> viewState.showDialogInterviewMaterials()
            ChatOption.DISABLE_NOTIFICATIONS -> viewState.showDisableNotifications()
            ChatOption.CLEAR_THE_HISTORY -> viewState.showClearTheHistory()
            ChatOption.ADD_PARTICIPANT -> viewState.showDialogAddParticipant()
        }
    }

    fun onChatAttachmentOptionClick(chatAttachmentOption: ChatAttachmentOption) {
        when (chatAttachmentOption) {
            ChatAttachmentOption.PHOTO_OR_VIDEO -> viewState.showPhotoOrVideo()
            ChatAttachmentOption.FILE -> viewState.showFile()
            ChatAttachmentOption.CONTACT -> viewState.showContact()
            ChatAttachmentOption.INTERROGATION -> viewState.showInterrogation()
        }
    }

    fun onPhotoAttachmentClick(attachmentPhotoEntity: AttachmentPhotoEntity) {

    }

    fun toGroupScreen() {
        router.navigateTo(Screens.GroupScreen(isProfile = false))
    }

    fun onMessageLongClick() {
        isMessagesSelected = true
        viewState.toggleSelectionMode(isMessagesSelected)
    }


    fun exit() {
        if (isMessagesSelected) {
            isMessagesSelected = false
            viewState.toggleSelectionMode(isMessagesSelected)
        } else {
            router.exit()
        }
    }
}