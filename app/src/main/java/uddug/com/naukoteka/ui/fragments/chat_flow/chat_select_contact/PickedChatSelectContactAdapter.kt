package uddug.com.naukoteka.ui.fragments.chat_flow.chat_select_contact

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import uddug.com.domain.repositories.ChatContact
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemSelectedContactsTopBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class PickedChatSelectContactAdapter(private val onContactClick: (ChatContact) -> Unit) :
    BaseAdapter<ChatContact, PickedChatSelectContactAdapter.ViewHolder>() {

    override fun newViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PickedChatSelectContactAdapter.ViewHolder =
        ViewHolder(R.layout.list_item_selected_contacts_top, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<ChatContact>(layoutRes, parent) {

        private val rootView: ListItemSelectedContactsTopBinding
            get() = ListItemSelectedContactsTopBinding.bind(
                itemView
            )

        override fun updateView(item: ChatContact) {
            rootView.run {
                item.run {
                    tvNameContact.text = item.name
                    itemView.setOnClickListener {
                        onContactClick.invoke(item)
                    }
                }
            }
        }
    }

}