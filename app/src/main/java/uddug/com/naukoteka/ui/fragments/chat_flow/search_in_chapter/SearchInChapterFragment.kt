package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentSearchInChapterBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.profile.ChatDetailInfoAdapter
import uddug.com.naukoteka.utils.ui.afterTextChangedDelay
import uddug.com.naukoteka.utils.viewBinding

class SearchInChapterFragment : BaseFragment(R.layout.fragment_search_in_chapter) {

    override val contentView by viewBinding(FragmentSearchInChapterBinding::bind)

    private lateinit var profileAdapter: ChatDetailInfoAdapter

    private val titles = arrayListOf(
        R.string.chats,
        R.string.media,
        R.string.links,
        R.string.files,
        R.string.audio,
    )

    private var localQuery: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        }
    }

    override fun onPause() {
        super.onPause()
        contentView.viewPager.adapter = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initAdapter() {
        profileAdapter = ChatDetailInfoAdapter(this, isGlobalSearch = true)
        profileAdapter.notifyDataSetChanged()
    }

}