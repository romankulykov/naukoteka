package uddug.com.naukoteka.ui.fragments.chat_flow.chat_select_contact

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isGone
import uddug.com.domain.repositories.Section
import uddug.com.domain.repositories.SectionType
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemSelectedContactsBinding
import uddug.com.naukoteka.global.base.BaseStickyAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class ChatSelectContactAdapter(
    private val onContactClick: (UserChatPreview) -> Unit
) :
    BaseStickyAdapter<Section, BaseViewHolder<Section>>() {

    override val listItemView: Int = R.layout.list_item_selected_contacts

    override fun newItemViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Section> =
        ViewHolder(listItemView, parent)

    private var selectedChatContactsList: List<UserChatPreview>? = null

    fun updateSelectedChatContactsList(selectedChatContactsList: List<UserChatPreview>) {
        this.selectedChatContactsList = selectedChatContactsList
        notifyDataSetChanged()
    }

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<Section>(layoutRes, parent) {

        val binding get() = ListItemSelectedContactsBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        override fun updateView(item: Section) {
            if (item is UserChatPreview) {
                binding.run {
                    tvNameContact.text = item.getName()
                    tvNickname.text = "@" + item.nickname
                    cbSelectContact.isChecked =
                        selectedChatContactsList?.map { it.id }?.contains(item.id)
                            ?: false
                    root.setOnClickListener { onContactClick.invoke(item) }
                }
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