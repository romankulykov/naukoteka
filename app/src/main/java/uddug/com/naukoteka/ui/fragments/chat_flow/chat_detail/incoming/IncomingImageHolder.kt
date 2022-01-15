package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.incoming

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.stfalcon.chatkit.commons.models.MessageContentType
import com.stfalcon.chatkit.utils.DateFormatter
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ItemCustomIncomingImageMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.Payload
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.base.BaseImageMessageHolder
import uddug.com.naukoteka.utils.ui.load

class IncomingImageHolder(itemView: View, override var anyPayload: Any?) : BaseImageMessageHolder(itemView, anyPayload) {

    override val contentView: ItemCustomIncomingImageMessageBinding =
        ItemCustomIncomingImageMessageBinding.bind(itemView)

    lateinit var ivIncoming: ImageView
    lateinit var mProgressBar: ProgressBar

    override fun onBind(data: MessageContentType.Image) {
        super.onBind(data)
        with(contentView) {
            data.run {
                messageUserAvatar.load(user.avatar, placeholder = R.drawable.ic_placeholder_user)
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

                ivIncoming.run {
                    load(data.imageUrl, placeholder = R.drawable.ic_glide_image_error)
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                }
                itemView.setOnClickListener {
                    (payload as Payload).dropInActivity?.droppedInActivity(imageUrl!!)
                }
            }
        }
    }

}