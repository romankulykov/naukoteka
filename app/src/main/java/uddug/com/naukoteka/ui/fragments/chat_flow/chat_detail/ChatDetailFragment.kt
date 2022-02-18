package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail

import android.Manifest
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.messages.MessageInput.AttachmentsListener
import com.stfalcon.chatkit.messages.MessageInput.InputListener
import com.stfalcon.chatkit.messages.MessagesListAdapter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick
import toothpick.ktp.delegate.inject
import uddug.com.data.cache.user_uuid.UserUUIDCache
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.DialogType
import uddug.com.domain.repositories.dialogs.models.MessageType
import uddug.com.naukoteka.GlideApp
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatClickMenu
import uddug.com.naukoteka.data.ChatOption
import uddug.com.naukoteka.data.ChatOptionsDialog
import uddug.com.naukoteka.databinding.FragmentChatDetailBinding
import uddug.com.naukoteka.di.DI
import uddug.com.naukoteka.di.modules.ChatDetailModule
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
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.system_message.SystemMessageViewHolder
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.getColorCompat
import uddug.com.naukoteka.utils.ui.TextDrawable
import uddug.com.naukoteka.utils.ui.load
import uddug.com.naukoteka.utils.ui.wasOnlineTenMinutesAgo
import uddug.com.naukoteka.utils.viewBinding
import uddug.com.naukoteka.utils.withPermissions
import java.io.File

class ChatDetailFragment : BaseFragment(R.layout.fragment_chat_detail),
    ChatDetailView, BackButtonListener,
    InputListener,
    AttachmentsListener,
    MessagesListAdapter.OnMessageViewLongClickListener<ChatMessage>,
    MessagesListAdapter.OnMessageViewClickListener<ChatMessage>,
    MessagesListAdapter.OnLoadMoreListener,
    MessageHolders.ContentChecker<ChatMessage> {

    companion object {

        private const val CHAT_PREVIEW = "ChatDetailFragment.CHAT_PREVIEW"

        private const val CONTENT_TYPE_SYSTEM_MESSAGE: Byte = 1

        fun newInstance(chat: ChatPreview) = ChatDetailFragment().apply {
            arguments = bundleOf(CHAT_PREVIEW to chat)
        }
    }

    private val userUUID: UserUUIDCache by inject()

    private val chat get() = arguments?.getParcelable<ChatPreview>(CHAT_PREVIEW)!!

    var messagesAdapter: MessagesListAdapter<ChatMessage>? = null

    val senderId get() = userUUID.requireEntity

    var imageLoader: ImageLoader? = null

    private var holderPayload: Payload? = null
    private var attachmentOptionsDialog: AttachmentOptionsDialog? = null
    private var messageChatsParcelable: Parcelable? = null

    override val contentView by viewBinding(FragmentChatDetailBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatDetailPresenter

    @ProvidePresenter
    fun providePresenter(): ChatDetailPresenter {
        val scope = getScope().openSubScope(DI.CHAT_DETAIL_SCOPE)
        scope.installModules(ChatDetailModule(chat))
        return scope.getInstance(ChatDetailPresenter::class.java)
            .also { Toothpick.closeScope(DI.CHAT_DETAIL_SCOPE) }
    }

    override fun initChat(chat: ChatPreview) {
        contentView.run {
            tvChat.text = chat.dialogName
            tvCountPeopleOrStatus.text = if (chat.dialogType == DialogType.GROUP) {
                getString(R.string.participants_count_format, chat.users?.size.toString())
            } else {
                if (chat.interlocutor?.lastOnline.wasOnlineTenMinutesAgo()) {
                    getString(R.string.online)
                } else {
                    getString(R.string.recently)
                }
            }
            if (chat.dialogImage?.fullPath == null) {
                val previewTextImage = chat.dialogName!!.split(" ").filter { it.isNotBlank() }
                val drawable = TextDrawable.builder()
                    .buildRound(
                        text = previewTextImage.map { it.first() }.joinToString(""),
                        color = requireContext().getColorCompat(R.color.object_main)
                    )
                ivChatImage.setImageDrawable(drawable)
            } else {
                ivChatImage.load(
                    chat.dialogImage?.fullPath,
                    placeholder = R.drawable.ic_glide_image_error,
                    requestOptions = RequestOptions.centerCropTransform()
                )
            }
            input.setInputListener(this@ChatDetailFragment)
            input.setAttachmentsListener(this@ChatDetailFragment)
            ivMenu.setOnClickListener { showOptionsDialog() }
            clHeader.setOnClickListener { presenter.onHeaderClick() }
            ivArrowBack.setOnClickListener { onBackPressed() }
            tvCancel.setOnClickListener { onBackPressed() }
            ivDelete.setOnClickListener { showMessage(ToastInfo("Your custom click handler")) }
            ivCopy.setOnClickListener { showMessage(ToastInfo("Your custom click handler")) }
            ivLoad.setOnClickListener { showMessage(ToastInfo("Your custom click handler")) }
            ivForward.setOnClickListener { showMessage(ToastInfo("Your custom click handler")) }
            messagesList.setAdapter(messagesAdapter)
            messagesList.layoutManager?.onRestoreInstanceState(messageChatsParcelable)
        }
    }

    override fun onPause() {
        super.onPause()
        messageChatsParcelable = contentView.messagesList.layoutManager?.onSaveInstanceState()
    }

    override fun hasContentFor(message: ChatMessage?, type: Byte): Boolean {
        return when (type) {
            CONTENT_TYPE_SYSTEM_MESSAGE -> message?.type == MessageType.SYSTEM && message.text?.isNotEmpty() == true
            else -> false
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun onSubmit(input: CharSequence): Boolean {
        val files = attachmentOptionsDialog?.photoAttachmentAdapter?.selectedOptions ?: emptyList()
        files.forEach { presenter.addFile(File(it.path)) }
        attachmentOptionsDialog = null
        presenter.sendMessage(input.toString())
        return true
    }

    override fun onAddAttachments() {
        context?.withPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) {
            if (attachmentOptionsDialog == null) {
                attachmentOptionsDialog = AttachmentOptionsDialog(
                    requireActivity(), presenter::onChatAttachmentOptionClick
                ) {

                    presenter::onPhotoAttachmentClick
                }
            }
            attachmentOptionsDialog?.show()
        }
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int) {
        presenter.loadMore(page)
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

    override fun showMessages(messages: List<ChatMessage>, needToClear: Boolean) {
        if (needToClear) {
            messagesAdapter?.clear()
        }
        messagesAdapter?.addToEnd(messages, false)
    }

    override fun addToStart(message: ChatMessage) {
        messagesAdapter?.addToStart(message, true)
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

    override fun initChatAdapter() {
        holderPayload = Payload()

        imageLoader = ImageLoader { imageView: ImageView?, url: String?, payload: Any? ->
            if (imageView != null) {
                GlideApp.with(this)
                    .load(url)
                    .placeholder(R.drawable.ic_glide_image_error).into(imageView)
            }
        }

        val holdersConfig = MessageHolders()
            .registerContentType(
                CONTENT_TYPE_SYSTEM_MESSAGE,
                SystemMessageViewHolder::class.java,
                R.layout.item_custom_system_message,
                SystemMessageViewHolder::class.java,
                R.layout.item_custom_system_message, this
            )
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
    }

    fun showOptionsDialog() {
        ChatOptionsDialogType(
            requireActivity(), ChatOptionsDialog
        ) { presenter.onChatOptionClick(it as ChatOption) }.show()
    }
}