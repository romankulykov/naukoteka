package uddug.com.naukoteka.ui.adapters.additional_options

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import uddug.com.domain.entities.AdditionalOptionsEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemAdditionalOptionsBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class AdditionalOptionsAdapter :
    BaseAdapter<AdditionalOptionsEntity, AdditionalOptionsAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(R.layout.list_item_additional_options, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<AdditionalOptionsEntity>(layoutRes, parent) {

        private val rootView: ListItemAdditionalOptionsBinding
            get() = ListItemAdditionalOptionsBinding.bind(
                itemView
            )

        override fun updateView(item: AdditionalOptionsEntity) {
            rootView.run {
                item.run {
                    tvTextOption.text = getContext().getString(textOption)
                    ivOption.setImageResource(imageOption)
                }
                dividerOption.isVisible = adapterPosition < itemCount - 1
            }
        }
    }
}