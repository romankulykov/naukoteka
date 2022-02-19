package uddug.com.naukoteka.ui.fragments.chat_flow.chat_add_contact

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isGone
import com.bumptech.glide.request.RequestOptions
import uddug.com.domain.repositories.Section
import uddug.com.domain.repositories.SectionType
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemContactBinding
import uddug.com.naukoteka.global.base.BaseStickyAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder
import uddug.com.naukoteka.utils.getColorCompat
import uddug.com.naukoteka.utils.ui.TextDrawable
import uddug.com.naukoteka.utils.ui.load

class ChatAddContactAdapter(private val userClick: (UserChatPreview) -> Unit) :
    BaseStickyAdapter<Section, BaseViewHolder<Section>>() {

    override val listItemView: Int = R.layout.list_item_contact

    override fun newItemViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Section> =
        ViewHolder(listItemView, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<Section>(layoutRes, parent) {

        @SuppressLint("SetTextI18n")
        override fun updateView(item: Section) {
            val binding = ListItemContactBinding.bind(itemView)
            binding.run {
                tvNameContact.text = item.getName()
                if (item is UserChatPreview) {
                    if (item.avatar.isNullOrEmpty()) {
                        val previewTextImage = item.getName().split(" ").filter { it.isNotBlank() }
                        val drawable = TextDrawable.builder()
                            .buildRound(
                                text = previewTextImage.map { it.first() }.joinToString(""),
                                color = getContext().getColorCompat(R.color.object_main)
                            )
                        ivContact.setImageDrawable(drawable)
                    } else {
                        ivContact.load(
                            item.avatar,
                            requestOptions = RequestOptions.centerCropTransform()
                        )
                    }
                    tvNickname.text = "@" + item.nickname
                    root.setOnClickListener { userClick(item) }
                }
                viewDivider.isGone = item.positionInSection == item.maxPosition
            }
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