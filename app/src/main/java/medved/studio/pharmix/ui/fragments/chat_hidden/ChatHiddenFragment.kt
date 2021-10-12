package medved.studio.pharmix.ui.fragments.chat_hidden

import android.os.Bundle
import android.view.View
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentChatHiddenBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.chat_hidden.ChatHiddenPresenter
import medved.studio.pharmix.presentation.chat_hidden.ChatHiddenView
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ChatHiddenFragment: BaseFragment(R.layout.fragment_chat_hidden),
    ChatHiddenView, BackButtonListener {

    override val contentView by viewBinding(FragmentChatHiddenBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatHiddenPresenter

    @ProvidePresenter
    fun providePresenter(): ChatHiddenPresenter {
        return getScope().getInstance(ChatHiddenPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }
}