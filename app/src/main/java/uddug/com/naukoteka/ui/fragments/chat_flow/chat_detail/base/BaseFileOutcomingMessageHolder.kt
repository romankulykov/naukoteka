package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.base

import android.annotation.SuppressLint
import android.view.View
import android.widget.CheckBox
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.MessageContentType
import com.stfalcon.chatkit.messages.MessageHolders
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.utils.logging.ILogger
import uddug.com.naukoteka.R
import uddug.com.naukoteka.di.DI
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.ChatSelectionStatus
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.Payload

abstract class BaseFileOutcomingMessageHolder(itemView: View, open var anyPayload: Any?) :
    MessageHolders.OutcomingTextMessageViewHolder<MessageContentType>(itemView, anyPayload) {

    abstract val contentView: ViewBinding
    var cbSelected: CheckBox? = null
    var isSelectionMode = false
    var message: IMessage? = null
    var selectedMessage: IMessage? = null

    val logger: ILogger by inject()

    open fun getScope(): Scope {
        return KTP.openRootScope()
            .openSubScope(DI.APP_SCOPE)
            .openSubScope(DI.MAIN_ACTIVITY_SCOPE)
    }

    override fun onBind(data: MessageContentType) {
        getScope()
            .inject(this)

        message = data
        initDropInChat()
        cbSelected = contentView.root.findViewById(R.id.cb_selected)
        isSelectionMode = (payload as? Payload)?.isSelectionMode == true
        if (selectedMessage?.id != message?.id) {
            selectedMessage =
                (payload as Payload).getPossibleSelectedMessage((message as ChatMessage).id)
        }

        logger.debug("message = ${message?.text} author = ${message?.user?.name}")
        logger.debug("cbSelected = ${cbSelected == null} isSelectionMode = $isSelectionMode")
        logger.debug("isSelected = ${message?.id == selectedMessage?.id}")

        cbSelected?.isVisible = isSelectionMode
        cbSelected?.isChecked = message?.id == selectedMessage?.id
    }


    @SuppressLint("CheckResult")
    fun initDropInChat() {
        if (anyPayload != null) {
            if (anyPayload is Payload) {
                (payload as Payload).dropInChat.subscribe { data ->
                    if (data is ChatSelectionStatus) {
                        when (data) {
                            is ChatSelectionStatus.ToggleSelectionMode -> {
                                isSelectionMode = data.isSelectionState
                                /*if (message == data.message) {
                                    selectedMessage = data.message
                                }*/
                            }
                            is ChatSelectionStatus.ToggleSelectionMessage -> {
                                if (message == data.message) {
                                    selectedMessage = if (data.isSelected) data.message else null
                                }
                            }
                        }
                        // for select\unselect message
                        logger.debug("cbSelected?.isChecked = ${message?.id == selectedMessage?.id}")
                        cbSelected?.isChecked = message?.id == selectedMessage?.id

                        cbSelected?.isVisible = isSelectionMode
                        if (!isSelectionMode) {
                            logger.debug("selectionMode = false")
                            selectedMessage = null
                            cbSelected?.isChecked = false
                        }
                    }
                }
                // if need something drop in activity
                //(payload as Payload).dropInActivity?.droppedInActivity(Any())
            }
        }
    }

}