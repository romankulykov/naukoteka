package uddug.com.naukoteka.presentation.chat_detail

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.entities.AttachmentPhotoEntity
import uddug.com.naukoteka.data_model.ChatAttachmentOption
import uddug.com.naukoteka.data_model.ChatOption
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter

@InjectConstructor
@InjectViewState
open class ChatDetailPresenter(val router: AppRouter) :
    BasePresenterImpl<ChatDetailView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
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

    fun exit() {
        router.exit()
    }
}