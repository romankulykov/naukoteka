package uddug.com.naukoteka.ui.fragments.create_group

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isGone
import uddug.com.domain.repositories.contacts.models.ChatContact
import uddug.com.domain.repositories.contacts.models.Section
import uddug.com.domain.repositories.participants.ParticipantsEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemParticipantsBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class CreateGroupAdapter(private val onParticipantRemove: (ParticipantsEntity) -> Unit) :
    BaseAdapter<ParticipantsEntity, CreateGroupAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): CreateGroupAdapter.ViewHolder =
        ViewHolder(R.layout.list_item_participants, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<ParticipantsEntity>(layoutRes, parent) {

        override fun updateView(item: ParticipantsEntity) {
            val binding = ListItemParticipantsBinding.bind(itemView)
            binding.tvNameParticipant.text = item.name
            binding.tvStatusParticipant.text = item.status
            binding.viewDivider.isGone = adapterPosition == itemCount - 1
            binding.ivRemove.setOnClickListener {
                onParticipantRemove.invoke(item)
            }
        }
    }
}