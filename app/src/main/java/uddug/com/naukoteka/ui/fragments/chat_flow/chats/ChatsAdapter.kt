package uddug.com.naukoteka.ui.fragments.chat_flow.chats

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.bumptech.glide.request.RequestOptions
import com.daimajia.swipe.SwipeLayout
import uddug.com.domain.repositories.dialogs.models.ChatMessageUI
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.DialogType
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatSwipeTitleOption
import uddug.com.naukoteka.databinding.ListItemChatBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder
import uddug.com.naukoteka.utils.getColorCompat
import uddug.com.naukoteka.utils.ui.TextDrawable
import uddug.com.naukoteka.utils.ui.getDateFormatChatList
import uddug.com.naukoteka.utils.ui.load

data class ChatSwipeParams(
    val chatListEntity: ChatPreview,
    val chatSwipeOption: ChatSwipeTitleOption
)

class ChatsAdapter(
    private val onChatClick: (ChatMessageUI) -> Unit,
    private val onChatLongClick: (ChatPreview, View) -> Unit,
    private val onSwipeClick: (ChatSwipeParams) -> Unit
) :
    BaseAdapter<ChatMessageUI, ChatsAdapter.ViewHolder>() {

    private var visibleItemView: View? = null

    override fun newViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(R.layout.list_item_chat, parent)

    override fun getPosition(newItem: ChatMessageUI): Int? {
        return getItems()?.indexOfFirst { it.dialogId == newItem.dialogId }
    }

    private var onlineUsersUUIDs = arrayListOf<String>()

    fun updateStatuses(onlineUsers: List<String>) {
        onlineUsersUUIDs.addAll(onlineUsers)
        notifyDataSetChanged()
    }

    fun getHeightItem() = visibleItemView?.height ?: 0

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<ChatMessageUI>(layoutRes, parent) {

        private val rootView: ListItemChatBinding get() = ListItemChatBinding.bind(itemView)

        override fun updateView(item: ChatMessageUI) {
            rootView.run {
                item.run {
                    viewTop.isInvisible = adapterPosition == 0

                    ivLabelNotifications.isVisible =
                        (item as? ChatPreview)?.isNotificationEnabled == false
                    tvNameContact.text = dialogName
                    tvTextOfMessage.text = message
                    val lastMessageExist = messageCreatedAt != null
                    tvTime.isVisible = lastMessageExist
                    messageCreatedAt?.let { lastMessageDate ->
                        tvTime.text = lastMessageDate.getDateFormatChatList()
                    }
                    if (dialogImage?.fullPath == null) {
                        val previewTextImage = dialogName.split(" ").filter { it.isNotBlank() }
                        val drawable = TextDrawable.builder()
                            .buildRound(
                                text = previewTextImage.map { it.first() }.joinToString(""),
                                color = getContext().getColorCompat(R.color.object_main)
                            )
                        ivPhoto.setImageDrawable(drawable)
                    } else {
                        ivPhoto.load(
                            dialogImage?.fullPath,
                            placeholder = R.drawable.ic_glide_image_error,
                            requestOptions = RequestOptions.centerCropTransform()
                        )
                    }
                    clChat.setOnClickListener { onChatClick.invoke(this) }
                    tvCountMessage.isVisible = item is ChatPreview

                    swipeLayout.isSwipeEnabled = item is ChatPreview

                    if (item is ChatPreview) initChatPreview(item)
                    itemView.post { visibleItemView = itemView }
                }
            }
        }

        private fun ListItemChatBinding.initChatPreview(chatPreview: ChatPreview) {
            chatPreview.run {
                val isThereUnreadMessage = unreadMessages != null && unreadMessages!! > 0
                tvCountMessage.isVisible = isThereUnreadMessage
                tvCountMessage.text = unreadMessages.toString()
                ivOnlineIndicator.isVisible =
                    onlineUsersUUIDs.contains(interlocutor?.userId) == true
                clChat.setBackgroundColor(getContext().getColorCompat(if (isPinned) R.color.object_disabled else R.color.text_main))
                clChat.setOnLongClickListener {
                    onChatLongClick.invoke(this, itemView)
                    true
                }
                swipeLayout.addDrag(SwipeLayout.DragEdge.Right, clSwipeContentRight)
                swipeLayout.addDrag(SwipeLayout.DragEdge.Left, clSwipeContentLeft)
                ivPin.isVisible = isPinned
                clearButtonBgView.run {
                    setOnClickListener {
                        onSwipeClick(ChatSwipeParams(chatPreview, ChatSwipeTitleOption.CLEAR))
                    }
                    isVisible = dialogType == DialogType.PERSONAL
                }
                blockButtonBgView.run {
                    setOnClickListener {
                        onSwipeClick(ChatSwipeParams(chatPreview, ChatSwipeTitleOption.BLOCK))
                    }
                    isVisible = dialogType == DialogType.PERSONAL
                }
                readButtonBgView.run {
                    setOnClickListener {
                        onSwipeClick(ChatSwipeParams(chatPreview, ChatSwipeTitleOption.READ))
                    }
                }
                anchorButtonBgView.run {
                    tvAnchorChat.setText(if (isPinned) R.string.chat_message_popup_menu_unpin else R.string.chat_message_popup_menu_pin)
                    setOnClickListener {
                        onSwipeClick(ChatSwipeParams(chatPreview, ChatSwipeTitleOption.PIN_TOGGLE))
                    }
                }
            }
        }

    }

}

