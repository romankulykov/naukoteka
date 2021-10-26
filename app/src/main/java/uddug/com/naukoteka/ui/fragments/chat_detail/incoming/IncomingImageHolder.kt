package com.mojipic.mojipic2.ui.chat.chat_items.incoming

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.stfalcon.chatkit.commons.models.MessageContentType
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.utils.DateFormatter
import uddug.com.naukoteka.GlideApp
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentChatDetailBinding
import uddug.com.naukoteka.databinding.ItemCustomIncomingImageMessageBinding
import uddug.com.naukoteka.databinding.ItemCustomOutcomingImageMessageBinding
import uddug.com.naukoteka.databinding.ItemCustomOutcomingTextMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_detail.IDropInChat
import uddug.com.naukoteka.ui.fragments.chat_detail.Payload
import uddug.com.naukoteka.utils.ui.load
import uddug.com.naukoteka.utils.viewBinding

class IncomingImageHolder(itemView: View, var anyPayload: Any?) :
    MessageHolders.BaseMessageViewHolder<MessageContentType.Image>(itemView, anyPayload) {

    val contentView: ItemCustomIncomingImageMessageBinding =
        ItemCustomIncomingImageMessageBinding.bind(itemView)

    lateinit var ivIncoming: ImageView
    lateinit var mProgressBar: ProgressBar

    override fun onBind(data: MessageContentType.Image) {
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
                messageTime.text = DateFormatter.format(createdAt, DateFormatter.Template.TIME)
                val radius = itemView.resources.getDimension(R.dimen.message_bubble_corners_radius)
                /*val shapeAppearanceModel = imageIncoming.shapeAppearanceModel.toBuilder()
                    .setTopRightCornerSize(radius)
                    .setBottomLeftCornerSize(radius)
                    .setBottomRightCornerSize(radius)
                    .build()
                imageIncoming.shapeAppearanceModel = shapeAppearanceModel*/

                ivIncoming = imageIncoming
                mProgressBar = progressBar
                GlideApp.with(itemView.context)
                    .load(
                        "https://pngimg.com/uploads/triangle/triangle_PNG30.png"
                    )
                    .placeholder(R.drawable.ic_glide_image_error).into(ivIncoming)
                ivIncoming.run {
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                }
                itemView.setOnClickListener {
                    (payload as Payload).dropInActivity?.droppedInActivity(imageUrl!!)
                }
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