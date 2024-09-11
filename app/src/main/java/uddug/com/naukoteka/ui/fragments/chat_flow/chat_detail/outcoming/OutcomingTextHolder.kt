package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.outcoming

import android.view.View
import android.widget.LinearLayout
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.utils.DateFormatter
import uddug.com.naukoteka.databinding.ItemCustomOutcomingTextMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.base.BaseMessageHolder

class OutcomingTextHolder(itemView: View, override var anyPayload: Any?) : BaseMessageHolder(itemView, anyPayload){

    override val contentView: ItemCustomOutcomingTextMessageBinding =
        ItemCustomOutcomingTextMessageBinding.bind(itemView)

    override fun onBind(data: IMessage) {
        super.onBind(data)
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

}