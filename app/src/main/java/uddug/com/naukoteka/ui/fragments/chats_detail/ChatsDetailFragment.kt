package uddug.com.naukoteka.ui.fragments.chats_detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.google.android.material.tabs.TabLayoutMediator
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentChatDetailsBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chats_detail.ChatsDetailPresenter
import uddug.com.naukoteka.presentation.chats_detail.ChatsDetailView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ChatsDetailFragment : BaseFragment(R.layout.fragment_chat_details),
    ChatsDetailView, BackButtonListener {

    override val contentView by viewBinding(FragmentChatDetailsBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatsDetailPresenter

    @ProvidePresenter
    fun providePresenter(): ChatsDetailPresenter {
        return getScope().getInstance(ChatsDetailPresenter::class.java)
    }

    val titles = arrayListOf(
        R.string.chat_all,
        R.string.chat_groups,
        R.string.chat_studies,
        R.string.chat_work,
        R.string.chat_hidden
    )

    companion object {
        private const val KEY_TITLE = "ChatsDetailFragment.KEY_TITLE"

        fun newInstance(titlesList: ArrayList<Int>?) = ChatsDetailFragment().apply {
            arguments = bundleOf().apply { putIntegerArrayList(KEY_TITLE, titlesList) }
        }
    }

    private val chatAdapter get() = ChatsDetailAdapter(this, titles)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            viewPager.adapter = chatAdapter
            viewPager.offscreenPageLimit = 5
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = context?.getString(titles[position])
            }.attach()
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }
}