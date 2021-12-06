package uddug.com.naukoteka.ui.fragments.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uddug.com.naukoteka.ui.fragments.audio.AudioFragment
import uddug.com.naukoteka.ui.fragments.files.FilesFragment
import uddug.com.naukoteka.ui.fragments.links.LinksFragment
import uddug.com.naukoteka.ui.fragments.media.MediaFragment

class ChatDetailInfoAdapter(
    private val fm: Fragment
) : FragmentStateAdapter(fm) {

    private val fragments =
        mutableListOf<Fragment>(MediaFragment(), LinksFragment(), FilesFragment(), AudioFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}