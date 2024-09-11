package uddug.com.naukoteka.ui.fragments.chat_flow.profile

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import uddug.com.naukoteka.R
import uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.audio.AudioFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.files.FilesFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.links.LinksFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.media.MediaFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.search_in_chat.SearchInChatsAndMessagesFragment

class ChatDetailInfoAdapter(
    private val fm: Fragment,
    private val withChatMessageSearch: Boolean = false,
    private val titles: ArrayList<Int>,
) : FragmentStatePagerAdapter(fm.childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    init {
        Log.d("TAG", "gag")
    }

    private val fragments =
        mutableListOf<Fragment>().apply {
            if (withChatMessageSearch) {
                add(SearchInChatsAndMessagesFragment())
            }
            addAll(listOf(MediaFragment(), LinksFragment(), FilesFragment(), AudioFragment()))
        }

    fun search(query: String) {
        fragments.filterIsInstance(SearchInChatsAndMessagesFragment::class.java)
            .first().search(query)
    }

    override fun getPageTitle(position: Int): CharSequence = fm.getString(titles[position])

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]

}