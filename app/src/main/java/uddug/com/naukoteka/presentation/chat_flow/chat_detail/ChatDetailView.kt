package uddug.com.naukoteka.presentation.chat_flow.chat_detail

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatDetailView : MvpView, LoadingView, InformativeView {

    fun showDialogSearchByConversation()
    fun showDialogInterviewMaterials()
    fun showDisableNotifications()
    fun showClearTheHistory()
    fun showDialogAddParticipant()
    fun showOptionsDialog()

    fun showPhotoOrVideo()
    fun showFile()
    fun showContact()
    fun showInterrogation()
    fun showAttachmentOptionDialog()
    fun showMessages(messages: List<ChatMessage>)
    fun toggleSelectionMode(messagesSelected: Boolean, message: ChatMessage? = null)
    fun toggleMessage(
        message: ChatMessage,
        selectedMessages: HashSet<ChatMessage>,
        isSelected: Boolean
    )

}