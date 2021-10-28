package uddug.com.naukoteka.ui.adapters.additional_options

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data_model.ChatOption
import uddug.com.naukoteka.databinding.ListItemAdditionalOptionsBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class AdditionalOptionsAdapter(private val onOptionClick: ((ChatOption) -> Unit)?) :
    BaseAdapter<ChatOption, AdditionalOptionsAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(R.layout.list_item_additional_options, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<ChatOption>(layoutRes, parent) {

        private val rootView: ListItemAdditionalOptionsBinding
            get() = ListItemAdditionalOptionsBinding.bind(
                itemView
            )

        override fun updateView(item: ChatOption) {
            rootView.run {
                item.run {
                    tvTextOption.text = getContext().getString(textResId)
                    ivOption.setImageResource(imageResId)
                }
                dividerOption.isVisible = adapterPosition < itemCount - 1
            }
        }
    }
}