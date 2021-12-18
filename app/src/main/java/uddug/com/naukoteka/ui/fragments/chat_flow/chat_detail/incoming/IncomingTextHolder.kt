package com.mojipic.mojipic2.ui.chat.chat_items.incoming

import android.view.View
import android.widget.LinearLayout
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.utils.DateFormatter
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ItemCustomIncomingTextMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.IDropInChat
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.Payload
import uddug.com.naukoteka.utils.ui.load

class IncomingTextHolder(itemView: View, var anyPayload: Any?) :
    MessageHolders.BaseMessageViewHolder<IMessage>(itemView, anyPayload) {

    val contentView: ItemCustomIncomingTextMessageBinding =
        ItemCustomIncomingTextMessageBinding.bind(itemView)

    override fun onBind(data: IMessage) {
        initDropInChat()
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