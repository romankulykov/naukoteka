package uddug.com.naukoteka.ui.fragments.chat_detail

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import com.mojipic.mojipic2.ui.chat.chat_items.incoming.IncomingImageHolder
import com.mojipic.mojipic2.ui.chat.chat_items.incoming.IncomingTextHolder
import com.mojipic.mojipic2.ui.chat.chat_items.outcoming.OutcomingImageHolder
import com.mojipic.mojipic2.ui.chat.chat_items.outcoming.OutcomingTextHolder
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.messages.MessageInput.AttachmentsListener
import com.stfalcon.chatkit.messages.MessageInput.InputListener
import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.stfalcon.chatkit.messages.MessagesListAdapter.OnMessageLongClickListener
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.naukoteka.GlideApp
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentChatDetailBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_detail.ChatDetailPresenter
import uddug.com.naukoteka.presentation.chat_detail.ChatDetailView
import uddug.com.naukoteka.ui.custom.square_toast.ToastInfo
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding
import java.util.*

class ChatDetailFragment : BaseFragment(R.layout.fragment_chat_detail),
    ChatDetailView, BackButtonListener, OnMessageLongClickListener<Message>,
    InputListener,
    AttachmentsListener, MessagesListAdapter.OnLoadMoreListener,
    MessagesListAdapter.SelectionListener {

    var messagesAdapter: MessagesListAdapter<Message>? = null

    private val TOTAL_MESSAGES_COUNT = 100

    val senderId = "0"
    var imageLoader: ImageLoader? = null

    private var selectionCount = 0
    private var lastLoadedDate: Date? = Date()

    private var holderPayload: Payload? = null

    val messagesFixture = MessagesFixture()

    override val contentView by viewBinding(FragmentChatDetailBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatDetailPresenter

    @ProvidePresenter
    fun providePresenter(): ChatDetailPresenter {
        return getScope().getInstance(ChatDetailPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.ivSendMessage.isEnabled = false
        contentView.input.setInputListener(this)
        contentView.input.setAttachmentsListener {

        }
        contentView.input.inputEditText.doAfterTextChanged {
            contentView.ivSendMessage.isEnabled = it?.isNotEmpty()!!
            if (contentView.ivSendMessage.isEnabled) {
                contentView.ivSendMessage.setOnClickListener {
                    contentView.input.setInputListener(this)
                }
            }
        }

        contentView.ivArrowBack.setOnClickListener { onBackPressed() }
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

    override fun onMessageLongClick(message: Message?) {
        showMessage(ToastInfo("Your custom long click handler"))
    }

    override fun onSubmit(input: CharSequence?): Boolean {
        messagesAdapter?.addToStart(
            messagesFixture.getTextMessage(input.toString()), true
        )
        return true
    }

    override fun onAddAttachments() {
        messagesAdapter?.addToStart(messagesFixture.getImageMessage(), true)
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int) {
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
            loadMessages()
        }
    }

    private fun loadMessages() {
        val messagesFixture = MessagesFixture()
        //imitation of internet connection
        Handler().postDelayed({
            val messages: ArrayList<Message> = messagesFixture.getMessages(lastLoadedDate)
            lastLoadedDate = messages[messages.size - 1].createdAt
            messagesAdapter?.addToEnd(messages, false)
        }, 1000)
    }

    override fun onSelectionChanged(count: Int) {
        this.selectionCount = count
    }

    override fun onStart() {
        super.onStart()
        messagesAdapter?.addToStart(messagesFixture.getTextMessage(), true)
    }

    private fun initAdapter() {
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
}