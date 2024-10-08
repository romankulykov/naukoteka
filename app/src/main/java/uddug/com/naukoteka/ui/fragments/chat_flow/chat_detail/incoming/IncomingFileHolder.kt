package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.incoming

import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.stfalcon.chatkit.commons.models.MessageContentType
import com.stfalcon.chatkit.utils.DateFormatter
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.DialogType
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ItemCustomIncomingFileMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.DropInChatEvent
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.Payload
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.base.BaseFileIncomingMessageHolder
import uddug.com.naukoteka.utils.ui.load

class IncomingFileHolder(itemView: View, override var anyPayload: Any?) : BaseFileIncomingMessageHolder(itemView, anyPayload) {

    override val contentView: ItemCustomIncomingFileMessageBinding =
        ItemCustomIncomingFileMessageBinding.bind(itemView)

    lateinit var mProgressBar: ProgressBar

    override fun onBind(data: MessageContentType) {
        super.onBind(data)
        with(contentView) {
            data.run {
                messageUserAvatar.run {
                    isVisible = (data as? ChatMessage)?.chatPreview?.dialogType == DialogType.GROUP
                    load(user.avatar, placeholder = R.drawable.ic_placeholder_user)
                }
                nameUser.run {
                    isVisible = (data as? ChatMessage)?.chatPreview?.dialogType == DialogType.GROUP
                    text = user.name
                }
                messageTime.text = DateFormatter.format(createdAt, DateFormatter.Template.TIME)

                mProgressBar = progressBar

                val lp = messageText.layoutParams.apply {
                    height = 0; width = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                messageText.layoutParams = lp
                messageText.text = text
                messageText.isGone = text.isNullOrBlank()

                if (data is ChatMessage) {
                    tvFileName.text = data.files.first().filename

                }
                itemView.setOnClickListener {
                    (data as? ChatMessage)?.let { chatMessage ->
                        (payload as Payload).dropInActivity?.droppedInActivity(
                            DropInChatEvent.DownloadFileEvent(
                                chatMessage.files.first().fullPath ?: ""
                            )
                        )
                    }
                }
            }
        }
    }

}