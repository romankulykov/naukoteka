package medved.studio.pharmix.ui.fragments.tutorial

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import medved.studio.domain.entities.TutorialEntity
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.ListItemPageBinding
import medved.studio.pharmix.global.base.BaseAdapter
import medved.studio.pharmix.global.base.BaseViewHolder

class TutorialAdapter : BaseAdapter<TutorialEntity, TutorialAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(R.layout.list_item_page, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<TutorialEntity>(layoutRes, parent) {

        private val rootView: ListItemPageBinding get() = ListItemPageBinding.bind(itemView)

        override fun updateView(item: TutorialEntity) {
            rootView.run {
                item.run {
                    tvTitle.text = getContext().getString(title)
                    ivImage.setImageResource(image)
                }
            }
        }
    }
}

