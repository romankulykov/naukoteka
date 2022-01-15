package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.messages.MessageInput.AttachmentsListener
import com.stfalcon.chatkit.messages.MessageInput.InputListener
import com.stfalcon.chatkit.messages.MessagesListAdapter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.ktp.delegate.inject
import uddug.com.data.cache.token.UserTokenCache
import uddug.com.data.cache.user_uuid.UserUUIDCache
import uddug.com.domain.repositories.dialogs.models.*
import uddug.com.naukoteka.GlideApp
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatClickMenu
import uddug.com.naukoteka.data.ChatOptionsDialog
import uddug.com.naukoteka.databinding.FragmentChatDetailBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.chat_detail.ChatDetailPresenter
import uddug.com.naukoteka.presentation.chat_flow.chat_detail.ChatDetailView
import uddug.com.naukoteka.ui.adapters.long_press_menu.LongPressMenuAdapter
import uddug.com.naukoteka.ui.custom.square_toast.ToastInfo
import uddug.com.naukoteka.ui.dialogs.chat_option.AttachmentOptionsDialog
import uddug.com.naukoteka.ui.dialogs.chat_option.ChatOptionsDialogType
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.incoming.IncomingImageHolder
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.incoming.IncomingTextHolder
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.outcoming.OutcomingImageHolder
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.outcoming.OutcomingTextHolder
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.ui.wasOnlineTenMinutesAgo
import uddug.com.naukoteka.utils.viewBinding
import java.util.*
import kotlin.random.Random

class ChatDetailFragment : BaseFragment(R.layout.fragment_chat_detail),
    ChatDetailView, BackButtonListener,
    InputListener,
    AttachmentsListener,
    MessagesListAdapter.OnMessageViewLongClickListener<ChatMessage>,
    MessagesListAdapter.OnMessageViewClickListener<ChatMessage>,
    MessagesListAdapter.OnLoadMoreListener {

    companion object {

        private const val CHAT_PREVIEW = "ChatDetailFragment.CHAT_PREVIEW"

        fun newInstance(chat: ChatPreview) = ChatDetailFragment().apply {
            arguments = bundleOf(CHAT_PREVIEW to chat)
        }
    }

    private val userUUID: UserUUIDCache by inject()

    private val chat get() = arguments?.getParcelable<ChatPreview>(CHAT_PREVIEW)

    var messagesAdapter: MessagesListAdapter<ChatMessage>? = null

    private val TOTAL_MESSAGES_COUNT = 100

    val senderId get() = userUUID.requireEntity

    var imageLoader: ImageLoader? = null

    private var selectionCount = 0

    private var holderPayload: Payload? = null

    override val contentView by viewBinding(FragmentChatDetailBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatDetailPresenter

    @ProvidePresenter
    fun providePresenter(): ChatDetailPresenter {
        return getScope().getInstance(ChatDetailPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getChat(chat!!)

        contentView.run {
            tvChat.text = chat?.dialogName
            tvCountPeopleOrStatus.text = if (chat?.dialogType == DialogType.GROUP) {
                getString(R.string.participants_count_format, chat?.users?.size.toString())
            } else {
                if (chat?.interlocutor?.lastOnline.wasOnlineTenMinutesAgo()) {
                    getString(R.string.online)
                } else {
                    getString(R.string.recently)
                }
            }
            input.setInputListener(this@ChatDetailFragment)
            input.setAttachmentsListener(this@ChatDetailFragment)
            ivMenu.setOnClickListener { showOptionsDialog() }
            clHeader.setOnClickListener { presenter.toGroupScreen() }
            ivArrowBack.setOnClickListener { onBackPressed() }
            tvCancel.setOnClickListener { onBackPressed() }
            ivDelete.setOnClickListener { showMessage(ToastInfo("Your custom click handler")) }
            ivCopy.setOnClickListener { showMessage(ToastInfo("Your custom click handler")) }
            ivLoad.setOnClickListener { showMessage(ToastInfo("Your custom click handler")) }
            ivForward.setOnClickListener { showMessage(ToastInfo("Your custom click handler")) }

        }
        initAdapter()
        imageLoader = ImageLoader { imageView: ImageView?, url: String?, payload: Any? ->
            if (imageView != null) {
                GlideApp.with(this)
                    .load(url)
                    .placeholder(R.drawable.ic_glide_image_error).into(imageView)
            }
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun onSubmit(input: CharSequence): Boolean {
        messagesAdapter?.addToStart(
            ChatMessage(
                Random(1000).nextInt(1, 9999),
                input.toString(),
                MessageType.TEXT,
                emptyList(),
                senderId,
                Calendar.getInstance(),
                emptyList(),
                UserChatPreview(
                    null,
                    senderId,
                    false,
                    "My Name is ",
                    "My nick name",
                    Calendar.getInstance()
                )
            ), true
        )
        presenter.sendMessage(input.toString())
        return true
    }

    override fun onAddAttachments() {
        showAttachmentOptionDialog()
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int) {
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
        }
    }

    override fun onMessageViewLongClick(view: View?, message: ChatMessage) {
        presenter.onMessageLongClick(message)
    }

    override fun onMessageViewClick(view: View, message: ChatMessage) {
        if (presenter.isMessagesSelected) {
            presenter.onMessageClick(message)
        } else {
            showPopupLongPressMenu(view, message)
        }
    }

    private fun showPopupLongPressMenu(v: View, message: ChatMessage) {
        contentView.run {
            val child =
                LayoutInflater.from(requireContext()).inflate(R.layout.popup_long_press_menu, null)
            val popupWindow = PopupWindow(requireContext())
            val longPressMenuAdapter = LongPressMenuAdapter { menuItem ->
                if (menuItem == ChatClickMenu.SELECT) {
                    presenter.onMessageLongClick(message)
                }
                popupWindow.dismiss()
            }
            with(popupWindow) {
                contentView = child
                setBackgroundDrawable(null)
                elevation = 12F
                isFocusable = true
                isOutsideTouchable = true
                showAsDropDown(v, 0, 0)
                with(child) {
                    findViewById<RecyclerView>(R.id.rv_popup_long_press_menu).adapter =
                        longPressMenuAdapter.apply { setItems(ChatClickMenu.values().toList()) }
                }
            }
        }
    }

    override fun showMessages(messages: List<ChatMessage>) {
        messagesAdapter?.addToEnd(messages, false)
    }

    override fun toggleSelectionMode(messagesSelected: Boolean, message: ChatMessage?) {
        holderPayload?.publish(ChatSelectionStatus.ToggleSelectionMode(messagesSelected, message))
        contentView.run {
            selectedMessagesOptionsLl.isVisible = messagesSelected
            tvCancel.isVisible = messagesSelected
            ivCall.isGone = messagesSelected
            ivVideoChat.isGone = messagesSelected
            ivMenu.isGone = messagesSelected
        }
    }

    override fun toggleMessage(
        message: ChatMessage,
        selectedMessages: HashSet<ChatMessage>,
        isSelected: Boolean
    ) {
        holderPayload?.publish(
            ChatSelectionStatus.ToggleSelectionMessage(
                message,
                selectedMessages.toList(),
                isSelected
            )
        )

    }

    private fun initAdapter() {
        holderPayload = Payload()

        val holdersConfig = MessageHolders()
            .setIncomingTextConfig(
                IncomingTextHolder::class.java,
                R.layout.item_custom_incoming_text_message,
                holderPayload
            )
            .setOutcomingTextConfig(
                OutcomingTextHolder::class.java,
                R.layout.item_custom_outcoming_text_message,
                holderPayload
            )
            .setIncomingImageConfig(
                IncomingImageHolder::class.java,
                R.layout.item_custom_incoming_image_message,
                holderPayload
            )
            .setOutcomingImageConfig(
                OutcomingImageHolder::class.java,
                R.layout.item_custom_outcoming_image_message,
                holderPayload
            )
        messagesAdapter =
            MessagesListAdapter(senderId, holdersConfig, imageLoader)
        messagesAdapter?.setOnMessageViewLongClickListener(this)
        messagesAdapter?.setOnMessageViewClickListener(this)
        messagesAdapter?.setLoadMoreListener(this)
        contentView.messagesList.setAdapter(messagesAdapter)
    }

    override fun showDialogSearchByConversation() {

    }

    override fun showDialogInterviewMaterials() {

    }

    override fun showDisableNotifications() {

    }

    override fun showClearTheHistory() {

    }

    override fun showDialogAddParticipant() {

    }

    override fun showOptionsDialog() {
        ChatOptionsDialogType(
            requireActivity(), ChatOptionsDialog
        ) { presenter::onChatOptionClick }.show()
    }

    override fun showPhotoOrVideo() {

    }

    override fun showFile() {

    }

    override fun showContact() {

    }

    override fun showInterrogation() {

    }

    override fun showAttachmentOptionDialog() {
        AttachmentOptionsDialog(
            requireActivity(), presenter::onChatAttachmentOptionClick,
            presenter::onPhotoAttachmentClick
        ).show()
    }

}