package uddug.com.naukoteka.ui.adapters.additional_options

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.BottomSheetDialogEntity
import uddug.com.naukoteka.data.BottomSheetDialogOption
import uddug.com.naukoteka.data.TitleDialogEntity
import uddug.com.naukoteka.databinding.ListItemAdditionalOptionsBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder
import uddug.com.naukoteka.utils.getColorCompat

class AdditionalOptionsAdapter(private val onOptionClick: ((BottomSheetDialogOption) -> Unit)?) :
    BaseAdapter<BottomSheetDialogEntity, AdditionalOptionsAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(R.layout.list_item_additional_options, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<BottomSheetDialogEntity>(layoutRes, parent) {

        private val rootView: ListItemAdditionalOptionsBinding
            get() = ListItemAdditionalOptionsBinding.bind(
                itemView
            )

        override fun updateView(item: BottomSheetDialogEntity) {
            rootView.run {
                item.run {
                    tvTextOption.run {
                        text = textResId?.let { context.getString(it) } ?: title
                        gravity = textGravity
                        if (item is TitleDialogEntity) {
                            setTextColor(context.getColorCompat(if (item.isClickable) R.color.text_inactive else R.color.main_red))
                        }
                    }
                    imageResId?.let { ivOption.setImageResource(it) }
                }
                dividerOption.isVisible = adapterPosition < itemCount - 1
                itemView.setOnClickListener {
                    if (item.isClickable) {
                        onOptionClick?.invoke(item.option)
                    }
                }
            }
        }
    }
}