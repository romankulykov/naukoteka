package uddug.com.naukoteka.presentation.chat_flow.chat_detail

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.entities.AttachmentPhotoEntity
import uddug.com.domain.interactors.dialogs.DialogsInteractor
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.utils.logging.ILogger
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
    private val logger: ILogger
) :
    BasePresenterImpl<ChatDetailView>() {

    private val pageLimit: Int = 10
    private var loadMore = false

    var chatPreview: ChatPreview? = null

    var isMessagesSelected = false
    var selectedMessages = hashSetOf<ChatMessage>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        dialogsInteractor.openSocket()
            .await {

            }
        dialogsInteractor.observe()
            .await(withProgress = false) { message ->
                logger.debug(message.toString())
                viewState.addToStart(message)
            }

    }

    fun getChat(chatPreview: ChatPreview) {
        this.chatPreview = chatPreview
        dialogsInteractor.getDialogMessages(chatPreview, pageLimit)
            .await {
                loadMore = it.size == pageLimit
                viewState.showMessages(it)
            }
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

    fun onHeaderClick() {
        if (chatPreview != null) {
            router.navigateTo(Screens.ChatInfoScreen(chatPreview!!))
        }
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

    fun sendMessage(message: String) {
        dialogsInteractor.pushTextMessage(chatPreview!!, message)
            .await {

            }
    }

    fun loadMore(page: Int) {
        if (loadMore /*&& page != dialogsInteractor.messages.size*/) {
            viewState.showLoading(true)
            getChat(chatPreview!!)
        }
    }


}
