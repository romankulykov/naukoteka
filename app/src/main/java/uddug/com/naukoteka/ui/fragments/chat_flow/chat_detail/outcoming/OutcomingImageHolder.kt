package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.outcoming

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.stfalcon.chatkit.commons.models.MessageContentType
import com.stfalcon.chatkit.utils.DateFormatter
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ItemCustomOutcomingImageMessageBinding
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.Payload
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.base.BaseImageMessageHolder
import uddug.com.naukoteka.utils.ui.load

class OutcomingImageHolder(itemView: View, override var anyPayload: Any?) :
    BaseImageMessageHolder(itemView, anyPayload) {

    lateinit var ivOutcoing: ImageView
    lateinit var mProgressBar: ProgressBar

    override val contentView: ItemCustomOutcomingImageMessageBinding =
        ItemCustomOutcomingImageMessageBinding.bind(itemView)

    override fun onBind(data: MessageContentType.Image) {
        super.onBind(data)
        with(contentView) {
            data.run {
                messageTime.text = DateFormatter.format(createdAt, DateFormatter.Template.TIME)
                ivOutcoing = imageOutcoming
                mProgressBar = progressBar

                ivOutcoing.run {
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