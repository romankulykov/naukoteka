package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.outcoming

import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isGone
import com.stfalcon.chatkit.commons.models.MessageContentType
import com.stfalcon.chatkit.utils.DateFormatter
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.naukoteka.databinding.ItemCustomOutcomingFileMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.DropInChatEvent
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.Payload
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.base.BaseFileOutcomingMessageHolder

class OutcomingFileHolder(itemView: View, override var anyPayload: Any?) :
    BaseFileOutcomingMessageHolder(itemView, anyPayload) {

    lateinit var mProgressBar: ProgressBar

    override val contentView: ItemCustomOutcomingFileMessageBinding =
        ItemCustomOutcomingFileMessageBinding.bind(itemView)

    override fun onBind(data: MessageContentType) {
        super.onBind(data)
        with(contentView) {
            data.run {
                messageTime.text = DateFormatter.format(createdAt, DateFormatter.Template.TIME)
                mProgressBar = progressBar

                val lp = messageText.layoutParams.apply {
                    height = 0; width = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                messageText.layoutParams = lp
                messageText.text = text
                messageText.isGone = text.isNullOrBlank()

                if (data is ChatMessage){
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