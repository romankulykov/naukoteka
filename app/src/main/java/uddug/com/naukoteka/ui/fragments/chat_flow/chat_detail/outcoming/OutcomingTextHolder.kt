package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.outcoming

import android.view.View
import android.widget.LinearLayout
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.utils.DateFormatter
import uddug.com.naukoteka.databinding.ItemCustomOutcomingTextMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.IDropInChat
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.Payload

class OutcomingTextHolder(itemView: View, var anyPayload: Any?) :
    MessageHolders.BaseMessageViewHolder<IMessage>(itemView, anyPayload) {

    val contentView: ItemCustomOutcomingTextMessageBinding =
        ItemCustomOutcomingTextMessageBinding.bind(itemView)

    override fun onBind(data: IMessage) {
        initDropInChat()
        with(contentView) {
            data.run {
                val lp = messageText.layoutParams.apply {
                    height = 0; width = LinearLayout.LayoutParams.WRAP_CONTENT;
                }
                messageText.layoutParams = lp
                messageText.text = text
                messageTime.text = DateFormatter.format(createdAt, DateFormatter.Template.TIME)
            }
        }
    }

    fun initDropInChat() {
        if (anyPayload != null) {
            if (anyPayload is Payload) {
                (payload as Payload).dropInChat = object : IDropInChat {
                    override fun droppedInChat(nothing: Any) {
                        // TODO when need to call something from activity to chat message
                    }
                }
                // if need something drop in activity
                //(payload as Payload).dropInActivity?.droppedInActivity(Any())
            }
        }
    }

}