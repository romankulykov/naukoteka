package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.incoming

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isGone
import android.widget.ProgressBar
import com.stfalcon.chatkit.commons.models.MessageContentType
import com.stfalcon.chatkit.utils.DateFormatter
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatMessageUI
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ItemCustomIncomingFileMessageBinding
import uddug.com.naukoteka.databinding.ItemCustomIncomingImageMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.DropInChatEvent
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.Payload
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.base.BaseFileMessageHolder
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.base.BaseImageMessageHolder
import uddug.com.naukoteka.utils.ui.load

class IncomingFileHolder(itemView: View, override var anyPayload: Any?) : BaseFileMessageHolder(itemView, anyPayload) {

    override val contentView: ItemCustomIncomingFileMessageBinding =
        ItemCustomIncomingFileMessageBinding.bind(itemView)

    lateinit var mProgressBar: ProgressBar

    override fun onBind(data: MessageContentType) {
        super.onBind(data)
        with(contentView) {
            data.run {
                messageUserAvatar.load(user.avatar, placeholder = R.drawable.ic_placeholder_user)
                nameUser.text = user.name
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