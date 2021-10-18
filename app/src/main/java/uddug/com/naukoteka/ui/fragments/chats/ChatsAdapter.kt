package uddug.com.naukoteka.ui.fragments.chats

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import uddug.com.domain.entities.ChatListEntity
import uddug.com.naukoteka.GlideApp
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemChatBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class ChatsAdapter : BaseAdapter<ChatListEntity, ChatsAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(R.layout.list_item_chat, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<ChatListEntity>(layoutRes, parent) {

        private val rootView: ListItemChatBinding get() = ListItemChatBinding.bind(itemView)

        override fun updateView(item: ChatListEntity) {
            rootView.run {
                item.run {
                    tvNameContact.text = getContext().getString(nameContact)
                    tvTextOfMessage.text = getContext().getString(textMessage)
                    GlideApp.with(itemView.context)
                        .load(
                            "https://img2.freepng.ru/20180402/ysw/kisspng-computer-icons-test" +
                                    "-survey-5ac2cf57df8907.6438385715227165039156.jpg"
                        )
                        .placeholder(R.drawable.ic_glide_image_error).into(ivPhoto)
                }
            }
        }
    }

}