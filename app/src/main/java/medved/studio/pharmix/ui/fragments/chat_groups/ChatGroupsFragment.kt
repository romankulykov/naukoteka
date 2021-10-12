package medved.studio.pharmix.ui.fragments.chat_groups

import android.os.Bundle
import android.view.View
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentChatGroupsBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.chat_groups.ChatGroupsPresenter
import medved.studio.pharmix.presentation.chat_groups.ChatGroupsView
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ChatGroupsFragment: BaseFragment(R.layout.fragment_chat_groups),
    ChatGroupsView, BackButtonListener {

    override val contentView by viewBinding(FragmentChatGroupsBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatGroupsPresenter

    @ProvidePresenter
    fun providePresenter(): ChatGroupsPresenter {
        return getScope().getInstance(ChatGroupsPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

}