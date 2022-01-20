package uddug.com.naukoteka.ui.fragments.chat_flow.chats

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import com.bumptech.glide.request.RequestOptions
import com.daimajia.swipe.SwipeLayout
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.DialogType
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatSwipeTitleOption
import uddug.com.naukoteka.databinding.ListItemChatBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder
import uddug.com.naukoteka.utils.ui.hourMinuteFormat
import uddug.com.naukoteka.utils.ui.isTodayDate
import uddug.com.naukoteka.utils.ui.load
import uddug.com.naukoteka.utils.ui.monthDateYearFormat

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
                    val lastMessageExist = lastMessage?.createdAt != null
                    tvTime.isVisible = lastMessageExist
                    lastMessage?.createdAt?.let { lastMessageDate ->
                        tvTime.text = if (lastMessageDate.isTodayDate()) {
                            lastMessageDate.hourMinuteFormat()
                        } else {
                            lastMessageDate.monthDateYearFormat()
                        }
                    }
                    val isThereUnreadMessage = unreadMessages != null && unreadMessages!! > 0
                    tvCountMessage.isVisible = isThereUnreadMessage
                    tvCountMessage.text = unreadMessages.toString()

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
                    clearButtonBgView.run {
                        setOnClickListener {
                            onSwipeClick(ChatSwipeParams(item, ChatSwipeTitleOption.CLEAR))
                        }
                        isVisible = dialogType == DialogType.PERSONAL
                    }
                    blockButtonBgView.run {
                        setOnClickListener {
                            onSwipeClick(ChatSwipeParams(item, ChatSwipeTitleOption.BLOCK))
                        }
                        isVisible = dialogType == DialogType.PERSONAL
                    }
                }
            }
        }
    }

}