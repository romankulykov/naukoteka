package uddug.com.naukoteka.ui.fragments.chat_flow.chats_list_detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uddug.com.naukoteka.ui.fragments.chat_flow.chats.ChatsFragment

class ChatsDetailAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val fragments =
        mutableListOf(
            ChatsFragment(),
            ChatsFragment(),
            ChatsFragment(),
            ChatsFragment(),
        )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

}