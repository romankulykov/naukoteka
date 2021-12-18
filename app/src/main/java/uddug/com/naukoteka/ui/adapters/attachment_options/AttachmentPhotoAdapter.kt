package uddug.com.naukoteka.ui.adapters.attachment_options

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import uddug.com.domain.entities.AttachmentPhotoEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemOptionsPhotosBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class AttachmentPhotoAdapter(private val onPhotoClick: ((AttachmentPhotoEntity) -> Unit)?) :
    BaseAdapter<AttachmentPhotoEntity, AttachmentPhotoAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(R.layout.list_item_options_photos, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<AttachmentPhotoEntity>(layoutRes, parent) {

        private val rootView: ListItemOptionsPhotosBinding
            get() = ListItemOptionsPhotosBinding.bind(
                itemView
            )

        override fun updateView(item: AttachmentPhotoEntity) {
            rootView.run {
                item.run {
                    ivPhoto.setImageResource(resourceImageId)
                    ivMakeAPhoto.isVisible = adapterPosition == 0
                    cbCheckPhoto.isVisible = adapterPosition != 0

                }
            }
        }
    }
}