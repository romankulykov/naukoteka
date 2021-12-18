package uddug.com.naukoteka.ui.fragments.chat_flow.chats_detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uddug.com.naukoteka.ui.fragments.chat_flow.chats.ChatsFragment

class ChatsDetailAdapter(fragment: Fragment, titles: ArrayList<Int>?) :
    FragmentStateAdapter(fragment) {

    private val fragments =
        mutableListOf(
            ChatsFragment.newInstance(titles),
            ChatsFragment.newInstance(titles),
            ChatsFragment.newInstance(titles),
            ChatsFragment.newInstance(titles),
            ChatsFragment.newInstance(titles),
        )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}