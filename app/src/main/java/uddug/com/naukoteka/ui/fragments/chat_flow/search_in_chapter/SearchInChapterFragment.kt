package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.subjects.BehaviorSubject
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentSearchInChapterBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.profile.ChatDetailInfoAdapter
import uddug.com.naukoteka.utils.viewBinding
import java.util.concurrent.TimeUnit

class SearchInChapterFragment : BaseFragment(R.layout.fragment_search_in_chapter) {

    override val contentView by viewBinding(FragmentSearchInChapterBinding::bind)

    private val profileAdapter by lazy { ChatDetailInfoAdapter(this, isGlobalSearch = true) }
    private val inputSearchSubject = BehaviorSubject.create<String>()

    private val titles = arrayListOf(
        R.string.chats,
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
            etSearchChat.doAfterTextChanged {
                val query = it.toString()
                ivClear.isVisible = query.isNotEmpty()
                if (query.isNotEmpty()) {
                    inputSearchSubject.onNext(query)
                }
            }
            ivClear.setOnClickListener { etSearchChat.setText("") }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = getString(titles[position])
            }.attach()
        }
        startListen()
    }

    @SuppressLint("CheckResult")
    fun startListen() {
        inputSearchSubject.debounce(700L, TimeUnit.MILLISECONDS)
            .subscribe { profileAdapter.search(it.toString()) }
    }

}