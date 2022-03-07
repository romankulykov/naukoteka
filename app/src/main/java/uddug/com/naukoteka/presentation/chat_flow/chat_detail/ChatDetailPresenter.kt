package uddug.com.naukoteka.presentation.chat_flow.chat_detail

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import io.reactivex.Observable
import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.entities.AndroidFileEntity
import uddug.com.domain.interactors.dialogs.DialogsInteractor
import uddug.com.domain.interactors.messages.MessagesInteractor
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.SearchMessagesInDialogs
import uddug.com.domain.utils.logging.ILogger
import uddug.com.naukoteka.data.ChatAttachmentOption
import uddug.com.naukoteka.data.ChatOption
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.DropInChatEvent
import uddug.com.naukoteka.utils.ui.toast
import uddug.com.naukoteka.utils.withPermissions
import java.io.File

@InjectConstructor
@InjectViewState
open class ChatDetailPresenter(
    val router: AppRouter,
    private val dialogsInteractor: DialogsInteractor,
    private val messagesInteractor: MessagesInteractor,
    private val chatPreview: ChatPreview,
    private val logger: ILogger,
    private val context: Context,
) :
    BasePresenterImpl<ChatDetailView>() {

    private val pageLimit: Int = 10
    private var loadMore = false
    private var files = arrayListOf<File>()

    var isMessagesSelected = false
    var selectedMessages = hashSetOf<ChatMessage>()

    var isSearchMode = false

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
                readMessages(listOf(message.id))
            }

    }

    private fun getChat(chatPreview: ChatPreview) {
        dialogsInteractor.getDialogMessages(chatPreview, pageLimit)
            .await {
                loadMore = it.size == pageLimit
                readMessages(it.map { it.id })
                viewState.showMessages(it)
            }
    }

    fun onChatOptionClick(chatOption: ChatOption) {
        when (chatOption) {
            ChatOption.SEARCH_BY_CONVERSATION -> toggleSearchMode(true)
            ChatOption.INTERVIEW_MATERIALS -> router.navigateTo(Screens.ChatInfoScreen(chatPreview))
            ChatOption.DISABLE_NOTIFICATIONS -> {}
            ChatOption.CLEAR_THE_HISTORY -> {
                dialogsInteractor.clearDialog(chatPreview.dialogId)
                    .await { router.exit() }
            }
            ChatOption.ADD_PARTICIPANT -> {}
        }
    }

    fun onChatAttachmentOptionClick(chatAttachmentOption: ChatAttachmentOption) {
        when (chatAttachmentOption) {
            /*ChatAttachmentOption.PHOTO_OR_VIDEO -> viewState.showPhotoOrVideo()*/
            ChatAttachmentOption.FILE -> viewState.pickFiles()
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

    private fun clearSelection() {
        isMessagesSelected = false
        viewState.toggleSelectionMode(isMessagesSelected)
        selectedMessages.clear()
    }

    private fun toggleSearchMode(flag: Boolean) {
        isSearchMode = flag
        viewState.toggleSearchMode(isSearchMode)
    }

    fun exit() {
        when {
            isMessagesSelected -> clearSelection()
            isSearchMode -> toggleSearchMode(false)
            else -> router.exit()
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

    fun readMessages(messages: List<Int>) {
        messagesInteractor.readMessages(chatPreview.dialogId, messages)
            .await { }
    }

    private fun downloadFile(url: String) {
        context.withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) {
            context.toast("Загрузка файла начата")
            val downloadManager =
                ContextCompat.getSystemService(context, DownloadManager::class.java)
            val request =
                DownloadManager.Request(Uri.parse(url))
            request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI or
                        DownloadManager.Request.NETWORK_MOBILE
            )
            val name = url.split("/").last()

            request.setTitle(name)
            request.setDescription("Downloading file...")

            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
            request.setMimeType("*/*")
            downloadManager!!.enqueue(request)
        }
    }

    fun handleDropInChatEvent(something: DropInChatEvent) {
        when (something) {
            is DropInChatEvent.DownloadFileEvent -> {
                router.navigateTo(Screens.OpenBrowser(something.url))
                //downloadFile(something.url)
            }
            is DropInChatEvent.ClickEvent -> {
                viewState.showPopupLongPressMenu(something)
            }
        }
    }

    fun search(query: String) {
        dialogsInteractor.searchMessagesInDialog(chatPreview.dialogId, query, 10)
            .await { foundMessages ->
                val foundedMessageIdToMessage = mutableMapOf<SearchMessagesInDialogs, ChatMessage>()
                dialogsInteractor.messages.forEach { localMessage ->
                    foundMessages.find { it.messageId == localMessage.id }?.let { searchMessageDialog ->
                        foundedMessageIdToMessage[searchMessageDialog] = localMessage
                    }
                }
                val foundedMessage = foundedMessageIdToMessage.values.first()
                val messagePosition = dialogsInteractor.messages.indexOf(foundedMessage)
                viewState.showFoundedMessages(messagePosition, foundedMessage, foundedMessageIdToMessage)
            }
    }

    fun findNextMessage(currentMessage: ChatMessage, foundedMessageIdToMessage: MutableMap<SearchMessagesInDialogs, ChatMessage>) {
        val messagesIterator = foundedMessageIdToMessage.values.toList().iterator()
        var nextMessage: ChatMessage?
        while (messagesIterator.hasNext()) {
            if (messagesIterator.next().id == currentMessage.id) {
                if (messagesIterator.hasNext()){
                    nextMessage = messagesIterator.next()
                    val messagePosition = dialogsInteractor.messages.indexOf(nextMessage)
                    viewState.showFoundedMessages(messagePosition, nextMessage, foundedMessageIdToMessage)
                    break
                }
            }
        }
    }

    fun findPreviousMessage(currentMessage: ChatMessage, foundedMessageIdToMessage: MutableMap<SearchMessagesInDialogs, ChatMessage>) {
        val messagesIterator = foundedMessageIdToMessage.values.toList().reversed().iterator()
        var nextMessage: ChatMessage?
        while (messagesIterator.hasNext()) {
            if (messagesIterator.next().id == currentMessage.id) {
                if (messagesIterator.hasNext()){
                    nextMessage = messagesIterator.next()
                    val messagePosition = dialogsInteractor.messages.indexOf(nextMessage)
                    viewState.showFoundedMessages(messagePosition, nextMessage, foundedMessageIdToMessage)
                    break
                }
            }
        }
    }


}
