package com.mojipic.mojipic2.ui.chat.chat_items.outcoming

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.stfalcon.chatkit.commons.models.MessageContentType
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.utils.DateFormatter
import uddug.com.naukoteka.GlideApp
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ItemCustomOutcomingImageMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.IDropInChat
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.Payload

class OutcomingImageHolder(itemView: View, var anyPayload: Any?) :
    MessageHolders.BaseMessageViewHolder<MessageContentType.Image>(itemView, anyPayload) {

    lateinit var ivOutcoing: ImageView
    lateinit var mProgressBar: ProgressBar

    val contentView: ItemCustomOutcomingImageMessageBinding =
        ItemCustomOutcomingImageMessageBinding.bind(itemView)

    override fun onBind(data: MessageContentType.Image) {
        initDropInChat()
        with(contentView) {
            data.run {
                messageTime.text = DateFormatter.format(createdAt, DateFormatter.Template.TIME)
                val radius = itemView.resources.getDimension(R.dimen.message_bubble_corners_radius)
                /*val shapeAppearanceModel = imageOutcoming.shapeAppearanceModel.toBuilder()
                    .setTopLeftCornerSize(radius)
                    .setBottomLeftCornerSize(radius)
                    .setBottomRightCornerSize(radius)
                    .build()
                imageOutcoming.shapeAppearanceModel = shapeAppearanceModel*/
                ivOutcoing = imageOutcoming
                mProgressBar = progressBar
                GlideApp.with(itemView.context)
                    .load(
                        "https://pngimg.com/uploads/triangle/triangle_PNG30.png"
                    )
                    .placeholder(R.drawable.ic_glide_image_error).into(ivOutcoing)
                ivOutcoing.run {
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