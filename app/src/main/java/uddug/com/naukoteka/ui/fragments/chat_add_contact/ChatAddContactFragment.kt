package uddug.com.naukoteka.ui.fragments.chat_add_contact

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.repositories.contacts.models.ChatContact
import uddug.com.domain.repositories.contacts.models.Section
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentChatAddContactBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_add_contact.ChatAddContactPresenter
import uddug.com.naukoteka.presentation.chat_add_contact.ChatAddContactView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class ChatAddContactFragment : BaseFragment(R.layout.fragment_chat_add_contact),
    ChatAddContactView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: ChatAddContactPresenter

    @ProvidePresenter
    fun providePresenter(): ChatAddContactPresenter {
        return getScope().getInstance(ChatAddContactPresenter::class.java)
    }

    private val chatAddContactAdapter = ChatAddContactAdapter()

    override val contentView by viewBinding(FragmentChatAddContactBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentView.run {
            clBack.setOnClickListener {
                onBackPressed()
            }
            etSearchContact.doAfterTextChanged {
                presenter.onQueryFilter(it.toString())
                ivSearch.isVisible = false
            }
            tvCreateGroup.setOnClickListener { presenter.toCreateGroup() }
            rvContacts.adapter = chatAddContactAdapter
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun showContacts(items: List<Section>) {
        chatAddContactAdapter.setItems(items)
    }
}