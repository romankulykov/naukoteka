package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.incoming

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.stfalcon.chatkit.commons.models.MessageContentType
import com.stfalcon.chatkit.utils.DateFormatter
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.DialogType
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ItemCustomIncomingImageMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.base.BaseImageMessageHolder
import uddug.com.naukoteka.utils.ui.load

class IncomingImageHolder(itemView: View, override var anyPayload: Any?) :
    BaseImageMessageHolder(itemView, anyPayload) {

    override val contentView: ItemCustomIncomingImageMessageBinding =
        ItemCustomIncomingImageMessageBinding.bind(itemView)

    lateinit var ivIncoming: ImageView
    lateinit var mProgressBar: ProgressBar

    override fun onBind(data: MessageContentType.Image) {
        super.onBind(data)
        with(contentView) {
            data.run {
                messageUserAvatar.run {
                    isVisible = (data as? ChatMessage)?.chatPreview?.dialogType == DialogType.GROUP
                    load(user.avatar, placeholder = R.drawable.ic_placeholder_user)
                }
                nameUser.run {
                    isVisible = (data as? ChatMessage)?.chatPreview?.dialogType == DialogType.GROUP
                    text = user.name
                }
                messageTime.text = DateFormatter.format(createdAt, DateFormatter.Template.TIME)

                ivIncoming = imageIncoming
                mProgressBar = progressBar

                ivIncoming.run {
                    load(data.imageUrl, placeholder = R.drawable.ic_glide_image_error)
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                }

                val lp = messageText.layoutParams.apply {
                    height = 0; width = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                messageText.layoutParams = lp
                messageText.text = text
                messageText.isGone = text.isNullOrBlank()

                itemView.setOnClickListener {
                    //(payload as Payload).dropInActivity?.droppedInActivity(imageUrl!!)
                }
            }
        }
    }

}