package uddug.com.naukoteka.ui.adapters.long_press_menu

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.PopupWindowMenu
import uddug.com.naukoteka.databinding.ListItemPopupMenuBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class LongPressMenuAdapter(private val onItemClick: (PopupWindowMenu) -> Unit) :
    BaseAdapter<PopupWindowMenu, LongPressMenuAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(R.layout.list_item_popup_menu, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<PopupWindowMenu>(layoutRes, parent) {

        private val rootView: ListItemPopupMenuBinding
            get() = ListItemPopupMenuBinding.bind(
                itemView
            )

        override fun updateView(item: PopupWindowMenu) {
            rootView.run {
                item.run {
                    tvTextOption.text = getContext().getString(textResId)
                    ivOption.setImageResource(imageResId)
                }
                dividerOption.isVisible = adapterPosition < itemCount - 1
                itemView.setOnClickListener { onItemClick(item) }
            }
        }
    }
}