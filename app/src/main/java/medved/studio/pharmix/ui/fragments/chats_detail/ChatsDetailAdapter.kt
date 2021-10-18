package medved.studio.pharmix.ui.fragments.chats_detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import medved.studio.pharmix.ui.fragments.chats.ChatsFragment

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