package uddug.com.naukoteka.ui.fragments.links

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import uddug.com.domain.entities.LinksEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemLinksBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class LinksAdapter :
    BaseAdapter<LinksEntity, LinksAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): LinksAdapter.ViewHolder =
        ViewHolder(R.layout.list_item_links, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<LinksEntity>(layoutRes, parent) {

        private val rootView: ListItemLinksBinding get() = ListItemLinksBinding.bind(itemView)

        override fun updateView(item: LinksEntity) {
            rootView.run {
                item.run {
                    tvUserName.text = userName
                    tvTitleLink.text = titleLink
                    tvLink.text = link
                }
            }
        }
    }

}