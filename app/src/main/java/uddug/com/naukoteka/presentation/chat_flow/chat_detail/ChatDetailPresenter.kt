package uddug.com.naukoteka.presentation.chat_flow.chat_detail

import io.reactivex.Observable
import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.entities.AndroidFileEntity
import uddug.com.domain.interactors.dialogs.DialogsInteractor
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.utils.logging.ILogger
import uddug.com.naukoteka.data.ChatAttachmentOption
import uddug.com.naukoteka.data.ChatOption
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens
import java.io.File

@InjectConstructor
@InjectViewState
open class ChatDetailPresenter(
    val router: AppRouter,
    private val dialogsInteractor: DialogsInteractor,
    private val chatPreview: ChatPreview,
    private val logger: ILogger
) :
    BasePresenterImpl<ChatDetailView>() {

    private val pageLimit: Int = 10
    private var loadMore = false
    private var files = arrayListOf<File>()

    var isMessagesSelected = false
    var selectedMessages = hashSetOf<ChatMessage>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initChatAdapter()
        viewState.initChat(chatPreview)
        getChat(chatPreview)
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
        dialogsInteractor.getDialogMessages(chatPreview, pageLimit)
            .await {
                loadMore = it.size == pageLimit
                viewState.showMessages(it)
            }
    }

    fun onChatOptionClick(chatOption: ChatOption) {
        when (chatOption) {
            ChatOption.SEARCH_BY_CONVERSATION -> router.navigateTo(Screens.SearchInChapterScreen())
            ChatOption.INTERVIEW_MATERIALS -> router.navigateTo(Screens.SearchInChapterScreen())
            ChatOption.DISABLE_NOTIFICATIONS -> {}
            ChatOption.CLEAR_THE_HISTORY -> {
                dialogsInteractor.deletePersonalDialog(chatPreview!!.dialogId)
                    .await { router.exit() }
            }
            ChatOption.ADD_PARTICIPANT -> {}
        }
    }

    fun onChatAttachmentOptionClick(chatAttachmentOption: ChatAttachmentOption) {
        when (chatAttachmentOption) {
            /*ChatAttachmentOption.PHOTO_OR_VIDEO -> viewState.showPhotoOrVideo()*/
            /*ChatAttachmentOption.FILE -> viewState.showFile()*/
            /*ChatAttachmentOption.CONTACT -> viewState.showContact()*/
            /*ChatAttachmentOption.INTERROGATION -> viewState.showInterrogation()*/
        }
    }

    fun onHeaderClick() {
        router.navigateTo(Screens.ChatInfoScreen(chatPreview))
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

    fun sendFiles(files: List<AndroidFileEntity>) {
        Observable.fromIterable(files)
            .flatMapCompletable {
                dialogsInteractor.pushTextMessage(chatPreview, null, listOf(File(it.path)))
            }
            .await { clearFiles() }
    }

    private fun clearFiles() {
        this.files.clear()
        viewState.clearFiles()
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

    fun sendMessage(text: String) {
        dialogsInteractor.pushTextMessage(chatPreview, text, files)
            .await { clearFiles() }
    }

    fun loadMore() {
        if (loadMore) {
            getChat(chatPreview)
        }
    }

    fun addFile(file: File) {
        files.add(file)
    }


}
