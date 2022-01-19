package uddug.com.naukoteka.ui.fragments.chat_flow.chat_add_contact

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isGone
import uddug.com.domain.repositories.Section
import uddug.com.domain.repositories.SectionType
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemSearchContactsBinding
import uddug.com.naukoteka.global.base.BaseStickyAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class ChatAddContactAdapter(private val userClick: (UserChatPreview) -> Unit) :
    BaseStickyAdapter<Section, BaseViewHolder<Section>>() {

    override val listItemView: Int = R.layout.list_item_search_contacts

    override fun newItemViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Section> =
        ViewHolder(listItemView, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<Section>(layoutRes, parent) {

        @SuppressLint("SetTextI18n")
        override fun updateView(item: Section) {
            val binding = ListItemSearchContactsBinding.bind(itemView)
            binding.tvNameContact.text = item.getName()
            if (item is UserChatPreview) {
                binding.tvNickname.text = "@" + item.nickname
                binding.root.setOnClickListener { userClick(item) }
            }
            binding.viewDivider.isGone = item.positionInSection == item.maxPosition
        }
    }

    override fun newItemHeaderViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Section> =
        HeaderViewHolder(listItemHeader, parent)

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        return getItem(itemPosition)?.sectionPosition ?: -1
    }

    override fun onBindHeaderViewHolder(p0: BaseViewHolder<Section>?, p1: Int) {
        if (p0 is HeaderViewHolder) {
            p0.textView.text = getItem(p1)?.sectionName()
        }
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): BaseViewHolder<Section> {
        return createViewHolder(parent, SectionType.Header.type)
    }
}