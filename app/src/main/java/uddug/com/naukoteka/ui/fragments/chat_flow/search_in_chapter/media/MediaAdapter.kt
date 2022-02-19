package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.media

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import uddug.com.domain.entities.MediaEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemMediaBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder
import uddug.com.naukoteka.utils.ui.load

class MediaAdapter :
    BaseAdapter<MediaEntity, MediaAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): MediaAdapter.ViewHolder =
        ViewHolder(R.layout.list_item_media, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<MediaEntity>(layoutRes, parent) {

        private val rootView: ListItemMediaBinding get() = ListItemMediaBinding.bind(itemView)

        override fun updateView(item: MediaEntity) {
            rootView.run {
                item.run {
                    ivImage.load(image)
                }
            }
        }
    }

}