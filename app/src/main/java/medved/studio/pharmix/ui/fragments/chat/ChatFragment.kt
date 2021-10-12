package medved.studio.pharmix.ui.fragments.chat

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentChatFullBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.chat.ChatPresenter
import medved.studio.pharmix.presentation.chat.ChatView
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ChatFragment : BaseFragment(R.layout.fragment_chat_full),
    ChatView, BackButtonListener {

    override val contentView by viewBinding(FragmentChatFullBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatPresenter

    @ProvidePresenter
    fun providePresenter(): ChatPresenter {
        return getScope().getInstance(ChatPresenter::class.java)
    }

    private val chatAdapter get() = ChatAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            viewPager.adapter = chatAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                val titles = listOf(
                    R.string.chat_all, R.string.chat_groups, R.string.chat_studies,
                    R.string.chat_work, R.string.chat_hidden
                )
                tab.text = context?.getString(titles[position])
            }.attach()
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }
}