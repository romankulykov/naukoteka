package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.search_in_chat

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.repositories.dialogs.models.SearchDialogs
import uddug.com.domain.repositories.dialogs.models.SearchMessagesInDialogs
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentSearchInChatsAndMessagesBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.search_in_chat.SearchInChatsAndMessagesPresenter
import uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.search_in_chat.SearchInChatsAndMessagesView
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_add_contact.ChatAddContactAdapter
import uddug.com.naukoteka.ui.fragments.chat_flow.chats.ChatsAdapter
import uddug.com.naukoteka.utils.viewBinding

class SearchInChatsAndMessagesFragment :
    BaseFragment(R.layout.fragment_search_in_chats_and_messages),
    SearchInChatsAndMessagesView {

    override val contentView by viewBinding(FragmentSearchInChatsAndMessagesBinding::bind)

    @InjectPresenter
    lateinit var presenter: SearchInChatsAndMessagesPresenter

    @ProvidePresenter
    fun providePresenter(): SearchInChatsAndMessagesPresenter {
        return getScope().getInstance(SearchInChatsAndMessagesPresenter::class.java)
    }

    private val chatsAdapter by lazy { ChatAddContactAdapter { presenter.onChatClick(it.userId.toInt()) } }
    private val messagesAdapter by lazy {
        ChatsAdapter({ presenter.onChatClick(it.dialogId) }, { _, _ -> }, {})
    }

    private var lastQuery: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            rvChats.adapter = chatsAdapter
            rvMessages.adapter = messagesAdapter
        }
    }

    override fun onPause() {
        super.onPause()
    }

    fun search(query: String) {
        presenter.search(query)
        presenter.searchMessages(query)
    }

    override fun showDialogs(dialogs: List<SearchDialogs>) {
        chatsAdapter.setItems(dialogs.map {
            it.run {
                UserChatPreview(
                    image = image,
                    userId = id.toString(),
                    isAdmin = false,
                    fullName = fullName,
                    nickname = nickname,
                    lastOnline = null
                )
            }
        })
        contentView.llChats.isVisible = dialogs.isNotEmpty()
    }

    override fun showMessages(messages: List<SearchMessagesInDialogs>, query: String) {
        if (this.lastQuery != query) {
            lastQuery = query
            messagesAdapter.clear()
        }
        messagesAdapter.addItems(messages)
        contentView.llMessages.isVisible = messages.isNotEmpty()
    }


}