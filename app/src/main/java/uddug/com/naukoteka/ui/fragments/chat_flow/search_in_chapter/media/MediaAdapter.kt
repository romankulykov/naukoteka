package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.media

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import uddug.com.domain.entities.MediaCategory
import uddug.com.domain.repositories.dialogs.models.SearchMedia
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemMediaBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder
import uddug.com.naukoteka.utils.ui.load

class MediaAdapter :
    BaseAdapter<SearchMedia, MediaAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): MediaAdapter.ViewHolder =
        ViewHolder(R.layout.list_item_media, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<SearchMedia>(layoutRes, parent) {

        private val rootView: ListItemMediaBinding get() = ListItemMediaBinding.bind(itemView)

        override fun updateView(item: SearchMedia) {
            rootView.run {
                item.run {
                    ivImage.load(attachment.fullPath)
                }
            }
        }
    }

}