package uddug.com.naukoteka.ui.fragments.chat_flow.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uddug.com.naukoteka.ui.fragments.chat_flow.audio.AudioFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.files.FilesFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.links.LinksFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.media.MediaFragment

class ChatDetailInfoAdapter(
    private val fm: Fragment
) : FragmentStateAdapter(fm) {

    private val fragments =
        mutableListOf<Fragment>(MediaFragment(), LinksFragment(), FilesFragment(), AudioFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}