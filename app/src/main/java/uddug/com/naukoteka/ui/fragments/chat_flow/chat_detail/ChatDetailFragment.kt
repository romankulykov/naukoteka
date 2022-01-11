package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.messages.MessageInput.AttachmentsListener
import com.stfalcon.chatkit.messages.MessageInput.InputListener
import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.stfalcon.chatkit.messages.MessagesListAdapter.OnMessageLongClickListener
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.repositories.dialogs.models.*
import uddug.com.naukoteka.GlideApp
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatOptionsDialog
import uddug.com.naukoteka.databinding.FragmentChatDetailBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.chat_detail.ChatDetailPresenter
import uddug.com.naukoteka.presentation.chat_flow.chat_detail.ChatDetailView
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
    OnMessageLongClickListener<ChatMessage>,
    InputListener,
    AttachmentsListener, MessagesListAdapter.OnLoadMoreListener,
    MessagesListAdapter.SelectionListener{

    companion object {

        private const val CHAT_PREVIEW = "ChatDetailFragment.CHAT_PREVIEW"

        fun newInstance(chat: ChatPreview) = ChatDetailFragment().apply {
            arguments = bundleOf(CHAT_PREVIEW to chat)
        }
    }

    private val chat get() = arguments?.getParcelable<ChatPreview>(CHAT_PREVIEW)

    var messagesAdapter: MessagesListAdapter<ChatMessage>? = null

    private val TOTAL_MESSAGES_COUNT = 100

    val senderId = "12345"
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
        messagesAdapter?.enableSelectionMode(this)
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

    override fun onMessageLongClick(message: ChatMessage?) {
    }

    override fun onSubmit(input: CharSequence?): Boolean {
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
        return true
    }

    override fun onAddAttachments() {
        showAttachmentOptionDialog()
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int) {
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
        }
    }

    override fun onSelectionChanged(count: Int) {
        Log.d("TAG", "onSelectionChanged: $count")
        presenter.onMessageLongClick()
        this.selectionCount = count
    }

    override fun showMessages(messages: List<ChatMessage>) {
        messagesAdapter?.addToEnd(messages, false)
    }

    override fun toggleSelectionMode(messagesSelected: Boolean) {
        holderPayload!!.isMessagesSelected.postValue(messagesSelected)
        if (messagesSelected) {
            holderPayload!!.selectedMessagesId.postValue(
                messagesAdapter!!.selectedMessages[messagesAdapter!!.selectedMessages.size - 1]
                    .id
            )
            contentView.run {
                selectedMessagesOptionsLl.visibility = View.VISIBLE
                tvCancel.visibility = View.VISIBLE
                llInput.visibility = View.GONE
                ivCall.visibility = View.GONE
                ivVideoChat.visibility = View.GONE
                ivMenu.visibility = View.GONE
            }
        } else {
            contentView.run {
                selectedMessagesOptionsLl.visibility = View.GONE
                tvCancel.visibility = View.GONE
                llInput.visibility = View.VISIBLE
                ivCall.visibility = View.VISIBLE
                ivVideoChat.visibility = View.VISIBLE
                ivMenu.visibility = View.VISIBLE
            }
        }
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
        messagesAdapter?.setOnMessageLongClickListener(this)
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