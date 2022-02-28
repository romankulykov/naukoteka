package uddug.com.naukoteka.ui.fragments.chat_flow.create_group

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemParticipantsBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder
import uddug.com.naukoteka.utils.ui.wasOnlineTenMinutesAgo
import java.util.*

class CreateGroupAdapter(private val onParticipantRemove: (UserChatPreview) -> Unit) :
    BaseAdapter<UserChatPreview, CreateGroupAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): CreateGroupAdapter.ViewHolder =
        ViewHolder(R.layout.list_item_participants, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<UserChatPreview>(layoutRes, parent) {

        override fun updateView(item: UserChatPreview) {
            val binding = ListItemParticipantsBinding.bind(itemView)
            binding.tvNameParticipant.text = item.name
            binding.tvStatusParticipant.isVisible = item.isOnline
            binding.tvLabelAdmin.isVisible = item.isAdmin
            binding.ivRemove.isGone = item.isAdmin
            binding.viewDivider.isGone = adapterPosition == itemCount - 1
            binding.ivRemove.setOnClickListener {
                onParticipantRemove.invoke(item)
            }
        }
    }
}
