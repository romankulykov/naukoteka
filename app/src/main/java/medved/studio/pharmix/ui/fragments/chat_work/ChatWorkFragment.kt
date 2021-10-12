package medved.studio.pharmix.ui.fragments.chat_work

import android.os.Bundle
import android.view.View
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentChatWorkBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.chat_work.ChatWorkPresenter
import medved.studio.pharmix.presentation.chat_work.ChatWorkView
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ChatWorkFragment : BaseFragment(R.layout.fragment_chat_work),
    ChatWorkView, BackButtonListener {

    override val contentView by viewBinding(FragmentChatWorkBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatWorkPresenter

    @ProvidePresenter
    fun providePresenter(): ChatWorkPresenter {
        return getScope().getInstance(ChatWorkPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }
}