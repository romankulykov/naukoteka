package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentSearchInChapterBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.profile.ChatDetailInfoAdapter
import uddug.com.naukoteka.utils.viewBinding

class SearchInChapterFragment : BaseFragment(R.layout.fragment_search_in_chapter) {

    override val contentView by viewBinding(FragmentSearchInChapterBinding::bind)

    private val profileAdapter by lazy { ChatDetailInfoAdapter(this) }

    private val titles = arrayListOf(
        R.string.media,
        R.string.links,
        R.string.files,
        R.string.audio,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            viewPager.adapter = profileAdapter
            tvCancel.setOnClickListener { requireActivity().onBackPressed() }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = getString(titles[position])
            }.attach()
        }
    }

}