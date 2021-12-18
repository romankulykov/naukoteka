package uddug.com.naukoteka.ui.adapters.attachment_options

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatAttachmentOption
import uddug.com.naukoteka.databinding.ListItemAttachmentOptionsBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class AttachmentOptionsAdapter(private val onAttachmentOptionClick: ((ChatAttachmentOption) -> Unit)?) :
    BaseAdapter<ChatAttachmentOption, AttachmentOptionsAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(R.layout.list_item_attachment_options, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<ChatAttachmentOption>(layoutRes, parent) {

        private val rootView: ListItemAttachmentOptionsBinding
            get() = ListItemAttachmentOptionsBinding.bind(
                itemView
            )

        override fun updateView(item: ChatAttachmentOption) {
            rootView.run {
                item.run {
                    tvTextOptionAttachment.text = getContext().getString(textResId)
                }
                dividerOption.isVisible = adapterPosition < itemCount - 1
            }
        }
    }
}