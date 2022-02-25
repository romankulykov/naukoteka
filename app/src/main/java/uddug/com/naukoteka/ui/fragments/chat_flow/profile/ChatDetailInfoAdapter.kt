package uddug.com.naukoteka.ui.fragments.chat_flow.profile

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.audio.AudioFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.files.FilesFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.links.LinksFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.media.MediaFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.search_in_chat.SearchInChatsAndMessagesFragment

class ChatDetailInfoAdapter(
    private val fm: Fragment,
    private val withChatMessageSearch: Boolean = false,
) : FragmentStateAdapter(fm) {

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

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}