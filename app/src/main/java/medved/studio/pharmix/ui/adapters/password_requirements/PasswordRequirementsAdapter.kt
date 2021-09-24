package medved.studio.pharmix.ui.adapters.password_requirements

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import medved.studio.domain.entities.PasswordRequirementsEntity
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.ItemPasswordRequirementsBinding
import medved.studio.pharmix.global.base.BaseAdapter
import medved.studio.pharmix.global.base.BaseViewHolder

class PasswordRequirementsAdapter :
    BaseAdapter<PasswordRequirementsEntity, PasswordRequirementsAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(R.layout.item_password_requirements, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<PasswordRequirementsEntity>(layoutRes, parent) {

        private val rootView: ItemPasswordRequirementsBinding
            get() = ItemPasswordRequirementsBinding.bind(
                itemView
            )

        override fun updateView(item: PasswordRequirementsEntity) {
            rootView.run {
                item.run {
                    tvPasswordRequirements.text = getContext().getString(passwordRequirement)
                }
            }
        }
    }
}