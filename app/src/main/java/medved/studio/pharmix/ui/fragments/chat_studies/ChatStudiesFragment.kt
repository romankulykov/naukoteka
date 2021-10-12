package medved.studio.pharmix.ui.fragments.chat_studies

import android.os.Bundle
import android.view.View
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentChatStudiesBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.chat_studies.ChatStudiesPresenter
import medved.studio.pharmix.presentation.chat_studies.ChatStudiesView
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ChatStudiesFragment: BaseFragment(R.layout.fragment_chat_studies),
    ChatStudiesView, BackButtonListener {

    override val contentView by viewBinding(FragmentChatStudiesBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatStudiesPresenter

    @ProvidePresenter
    fun providePresenter(): ChatStudiesPresenter {
        return getScope().getInstance(ChatStudiesPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }
}