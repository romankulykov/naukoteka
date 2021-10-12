package medved.studio.pharmix.ui.fragments.chat

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import medved.studio.pharmix.ui.fragments.chat_groups.ChatGroupsFragment
import medved.studio.pharmix.ui.fragments.chat_hidden.ChatHiddenFragment
import medved.studio.pharmix.ui.fragments.chat_list.ChatListFragment
import medved.studio.pharmix.ui.fragments.chat_studies.ChatStudiesFragment
import medved.studio.pharmix.ui.fragments.chat_work.ChatWorkFragment

class ChatAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val chatListFragment by lazy { ChatListFragment() }
    private val chatGroupsFragment by lazy { ChatGroupsFragment() }
    private val chatHiddenFragment by lazy { ChatHiddenFragment() }
    private val chatStudiesFragment by lazy { ChatStudiesFragment() }
    private val chatWorkFragment by lazy { ChatWorkFragment() }

    val fragments by lazy {
        listOf(
            chatListFragment,
            chatGroupsFragment,
            chatStudiesFragment,
            chatWorkFragment,
            chatHiddenFragment
        )
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> chatListFragment
            1 -> chatGroupsFragment
            2 -> chatStudiesFragment
            3 -> chatWorkFragment
            4 -> chatHiddenFragment
            else -> chatListFragment
        }
    }
}