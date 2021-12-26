package uddug.com.naukoteka.ui.fragments.chat_flow.chat_select_contact

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.repositories.ChatContact
import uddug.com.domain.repositories.Section
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentSelectContactBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.chat_select_contact.ChatSelectContactPresenter
import uddug.com.naukoteka.presentation.chat_flow.chat_select_contact.ChatSelectContactView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class ChatSelectContactFragment : BaseFragment(R.layout.fragment_select_contact),
    ChatSelectContactView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: ChatSelectContactPresenter

    @ProvidePresenter
    fun providePresenter(): ChatSelectContactPresenter {
        return getScope().getInstance(ChatSelectContactPresenter::class.java)
    }

    override val contentView by viewBinding(FragmentSelectContactBinding::bind)

    private val chatSelectContactAdapter by lazy { ChatSelectContactAdapter(presenter::onContactClicked) }

    private val pickedChatSelectContactAdapter by lazy { PickedChatSelectContactAdapter(presenter::removeSelectedContacts) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            tvCancel.setOnClickListener {
                onBackPressed()
            }
            etSearchContact.doAfterTextChanged {
                presenter.onQueryFilter(it.toString())
                ivSearch.isVisible = etSearchContact.text.isEmpty()
            }

            rvSelectedContacts.adapter = chatSelectContactAdapter
            rvSelectedContactsTop.adapter = pickedChatSelectContactAdapter
            tvSelected.text =
                getString(
                    R.string.selected_members,
                    0,
                    presenter.listOfChatContact.size
                )
            btnAddMembers.setOnClickListener { presenter.toCreateGroup() }
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun showContacts(items: List<Section>) {
        chatSelectContactAdapter.setItems(items)
    }

    override fun showSelectedContacts(items: List<ChatContact>, scrollToEnd: Boolean) {
        contentView.run {
            tvSelected.text =
                getString(
                    R.string.selected_members,
                    items.size,
                    presenter.listOfChatContact.size
                )
            btnAddMembers.text = getString(R.string.add_members, items.size)
            rvSelectedContactsTop.isGone = items.isEmpty()
            btnAddMembers.isGone = items.size < 2
            pickedChatSelectContactAdapter.setItems(items)
            chatSelectContactAdapter.updateSelectedChatContactsList(items)
            if (scrollToEnd && items.size > 1)
                rvSelectedContactsTop.smoothScrollToPosition(items.size - 1)
        }
    }
}