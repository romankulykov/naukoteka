package uddug.com.naukoteka.ui.fragments.chat_flow.chats

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.bumptech.glide.request.RequestOptions
import com.daimajia.swipe.SwipeLayout
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.naukoteka.GlideApp
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatSwipeTitleOption
import uddug.com.naukoteka.databinding.ListItemChatBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder
import uddug.com.naukoteka.utils.ui.load

data class ChatSwipeParams(
    val chatListEntity: ChatPreview,
    val chatSwipeOption: ChatSwipeTitleOption
)

class ChatsAdapter(
    private val onChatClick: (ChatPreview) -> Unit,
    private val onChatLongClick: (ChatPreview, View) -> Unit,
    private val onSwipeClick: (ChatSwipeParams) -> Unit
) :
    BaseAdapter<ChatPreview, ChatsAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(R.layout.list_item_chat, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<ChatPreview>(layoutRes, parent) {

        private val rootView: ListItemChatBinding get() = ListItemChatBinding.bind(itemView)

        override fun updateView(item: ChatPreview) {
            rootView.run {
                item.run {
                    tvNameContact.text = dialogName
                    tvTextOfMessage.text = lastMessage?.text
                    ivPhoto.load(
                        dialogImage?.fullPath,
                        placeholder = R.drawable.ic_glide_image_error,
                        requestOptions = RequestOptions.centerCropTransform()
                    )
                    clChat.setOnClickListener { onChatClick.invoke(item) }
                    clChat.setOnLongClickListener {
                        onChatLongClick.invoke(item, itemView)
                        true
                    }
                    swipeLayout.addDrag(SwipeLayout.DragEdge.Right, clSwipeContentRight)
                    swipeLayout.addDrag(SwipeLayout.DragEdge.Left, clSwipeContentLeft)
                    clearButtonBgView.setOnClickListener {
                        onSwipeClick(ChatSwipeParams(item, ChatSwipeTitleOption.CLEAR))
                    }
                    blockButtonBgView.setOnClickListener {
                        onSwipeClick(ChatSwipeParams(item, ChatSwipeTitleOption.BLOCK))
                    }
                }
            }
        }
    }

}