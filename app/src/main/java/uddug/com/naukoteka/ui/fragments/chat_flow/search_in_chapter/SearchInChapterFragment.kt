package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import toothpick.ktp.KTP
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentSearchInChapterBinding
import uddug.com.naukoteka.di.DI
import uddug.com.naukoteka.di.modules.SearchInChatModule
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.profile.ChatDetailInfoAdapter
import uddug.com.naukoteka.utils.showKeyboard
import uddug.com.naukoteka.utils.ui.afterTextChangedDelay
import uddug.com.naukoteka.utils.viewBinding

class SearchInChapterFragment : BaseFragment(R.layout.fragment_search_in_chapter) {

    companion object {

        private const val DIALOG_ID_KEY = "SearchInChapterFragment.DIALOG_ID_KEY"

        fun newInstance(dialogId: Int? = null) = SearchInChapterFragment().apply {
            arguments = bundleOf(DIALOG_ID_KEY to dialogId)
        }

    }

    private val dialogId get() = arguments?.getInt(DIALOG_ID_KEY, -1)

    override val contentView by viewBinding(FragmentSearchInChapterBinding::bind)

    private lateinit var profileAdapter: ChatDetailInfoAdapter

    private val titles
        get() = arrayListOf(
            if (dialogId == -1) R.string.chats else R.string.messages,
            R.string.media,
            R.string.links,
            R.string.files,
            R.string.audio,
        )

    private var localQuery: String = ""

    private val localScope by lazy { getScope().openSubScope(DI.SEARCH_IN_CHAT_SCOPE) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        KTP.closeScope(DI.SEARCH_IN_CHAT_SCOPE)
        localScope.installModules(SearchInChatModule(if (dialogId == -1) null else dialogId))
        initAdapter()
        contentView.run {
            viewPager.adapter = profileAdapter
            tvCancel.setOnClickListener { requireActivity().onBackPressed() }
            etSearchChat.afterTextChangedDelay {
                val query = it.toString()
                ivClear.isVisible = query.isNotEmpty()
                if (query.isNotEmpty()) {
                    profileAdapter.search(query)
                }
                localQuery = query
            }
            ivClear.setOnClickListener { etSearchChat.setText("") }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = getString(titles[position])
            }.attach()
            etSearchChat.requestFocus()
            requireContext().showKeyboard()
        }
    }

    override fun onPause() {
        super.onPause()
        contentView.viewPager.adapter = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapter() {
        profileAdapter = ChatDetailInfoAdapter(this, withChatMessageSearch = true)
        profileAdapter.notifyDataSetChanged()
    }

}