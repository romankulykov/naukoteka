package uddug.com.naukoteka.ui.adapters.attachment_options

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import uddug.com.domain.entities.AndroidFileEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemOptionsPhotosBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder
import uddug.com.naukoteka.utils.ui.load

class AttachmentPhotoAdapter(private val onPhotoClick: ((AndroidFileEntity) -> Unit)?) :
    BaseAdapter<AndroidFileEntity, AttachmentPhotoAdapter.ViewHolder>() {

    val selectedOptions = mutableSetOf<AndroidFileEntity>()

    override fun newViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(R.layout.list_item_options_photos, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<AndroidFileEntity>(layoutRes, parent) {

        private val rootView: ListItemOptionsPhotosBinding
            get() = ListItemOptionsPhotosBinding.bind(
                itemView
            )

        override fun updateView(item: AndroidFileEntity) {
            rootView.run {
                item.run {
                    ivPhoto.load(path)
                    ivMakeAPhoto.isVisible = adapterPosition == 0
                    cbCheckPhoto.isVisible = adapterPosition != 0
                    cbCheckPhoto.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {
                            selectedOptions.add(item)
                        } else {
                            selectedOptions.remove(item)
                        }
                    }

                }
            }
        }
    }
}