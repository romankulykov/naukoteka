package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.incoming

import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.utils.DateFormatter
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ItemCustomIncomingTextMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.IDropInChat
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.Payload
import uddug.com.naukoteka.utils.ui.load
import java.util.*

class IncomingTextHolder(itemView: View, var anyPayload: Any?) :
    MessageHolders.BaseMessageViewHolder<IMessage>(itemView, anyPayload) {

    val contentView: ItemCustomIncomingTextMessageBinding =
        ItemCustomIncomingTextMessageBinding.bind(itemView)

    override fun onBind(data: IMessage) {
        initDropInChat()
        //TODO refactor
        (payload as Payload).isMessagesSelected.observe(itemView.context as LifecycleOwner,
            {
                    data -> contentView.checkboxInTextMessage.isVisible = data
            })
        //
        with(contentView) {
            data.run {
                messageUserAvatar.load(
                    user.avatar,
                    error = R.drawable.ic_placeholder_user,
                    placeholder = R.drawable.ic_placeholder_user,
                    withAnimation = false
                )
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

    fun initDropInChat() {
        if (anyPayload != null) {
            if (anyPayload is Payload) {
                (payload as Payload).dropInChat = object : IDropInChat {
                    override fun droppedInChat(something: Any) {
                        // TODO when need to call something from activity to chat message
//                        if (something is Boolean) {
//                            contentView.checkboxInTextMessage.isVisible = something
//                        }
                    }
                }
                // if need something drop in activity
                //(payload as Payload).dropInActivity?.droppedInActivity(Any())
            }
        }
    }

}