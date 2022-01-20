package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.system_message

import android.view.View
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.MessageContentType
import com.stfalcon.chatkit.messages.MessageHolders
import uddug.com.naukoteka.databinding.ItemCustomSystemMessageBinding

class SystemMessageViewHolder(itemView: View, var anyPayload: Any?) :
    MessageHolders.IncomingTextMessageViewHolder<MessageContentType>(itemView, anyPayload) {

    val contentView: ItemCustomSystemMessageBinding =
        ItemCustomSystemMessageBinding.bind(itemView)

    override fun onBind(data: MessageContentType) {
        with(contentView) {
            data.run {
                tvStatusMessage.text = text
            }
        }
    }


}