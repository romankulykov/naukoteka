package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.incoming

import android.view.View
import android.widget.LinearLayout
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.utils.DateFormatter
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ItemCustomIncomingTextMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.base.BaseMessageHolder
import uddug.com.naukoteka.utils.ui.load

class IncomingTextHolder(itemView: View, override var anyPayload: Any?) :
    BaseMessageHolder(itemView, anyPayload) {

    override val contentView: ItemCustomIncomingTextMessageBinding =
        ItemCustomIncomingTextMessageBinding.bind(itemView)

    override fun onBind(data: IMessage) {
        super.onBind(data)
        with(contentView) {
            data.run {
                messageUserAvatar.load(user.avatar, placeholder = R.drawable.ic_placeholder_user)
                nameUser.text = user.name
                messageText.text = text
                val lp = messageText.layoutParams.apply {
                    height = 0; width = LinearLayout.LayoutParams.WRAP_CONTENT;
                }
                messageText.layoutParams = lp
                messageTime.text = DateFormatter.format(createdAt, DateFormatter.Template.TIME)
            }
        }
    }


}