package uddug.com.naukoteka.presentation.chat_flow.chat_detail

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
) : BasePresenterImpl<ChatDetailView>() {

    var chatPreview: ChatPreview? = null

    var isMessagesSelected = false
    var selectedMessages = hashSetOf<ChatMessage>()

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

    fun onMessageLongClick(message: ChatMessage) {
        if (!isMessagesSelected) {
            isMessagesSelected = true
            viewState.toggleSelectionMode(isMessagesSelected, message)
        }
        onMessageClick(message)
    }

    fun onMessageClick(message: ChatMessage) {
        if (isMessagesSelected) {
            val isSelect = if (selectedMessages.contains(message)) {
                selectedMessages.remove(message)
                false
            } else {
                selectedMessages.add(message)
                true
            }
            viewState.toggleMessage(message, selectedMessages, isSelect)
        } else {
            // TODO show context menu near message
        }
    }

    fun clearSelection() {
        isMessagesSelected = false
        viewState.toggleSelectionMode(isMessagesSelected)
        selectedMessages.clear()
    }


    fun exit() {
        if (isMessagesSelected) {
            clearSelection()
        } else {
            router.exit()
        }
    }
}