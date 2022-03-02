package uddug.com.naukoteka.ui.fragments.chat_flow.chats_list_detail

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentChatListBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.chats_list_detail.ChatsListDetailPresenter
import uddug.com.naukoteka.presentation.chat_flow.chats_list_detail.ChatsListDetailView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class ChatsListDetailFragment : BaseFragment(R.layout.fragment_chat_list),
    ChatsListDetailView, BackButtonListener {

    override val contentView by viewBinding(FragmentChatListBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatsListDetailPresenter

    @ProvidePresenter
    fun providePresenter(): ChatsListDetailPresenter {
        return getScope().getInstance(ChatsListDetailPresenter::class.java)
    }

    private val chatsAdapter get() =  ChatsDetailAdapter(this)

    val titles = arrayListOf(
        R.string.chat_all,
        R.string.chat_studies,
        R.string.chat_work,
        R.string.chat_hidden
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            viewPager.run {
                adapter = chatsAdapter
                offscreenPageLimit = 4
                isUserInputEnabled = false
            }
            chatEdit.setOnClickListener { presenter.showCreateChat() }
            tvChange.setOnClickListener {  }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = context?.getString(titles[position])
            }.attach()
            etSearchChat.setOnClickListener { presenter.navigateToSearchInChapter() }
            tvScienceChat.setOnClickListener { presenter.logout() }
        }
    }

    fun getAppBarLayoutHeight() = contentView.appBarLayoutChats.height

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }
}