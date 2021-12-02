package uddug.com.naukoteka.ui.fragments.profile

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import uddug.com.naukoteka.R
import uddug.com.naukoteka.ui.fragments.audio.AudioFragment
import uddug.com.naukoteka.ui.fragments.files.FilesFragment
import uddug.com.naukoteka.ui.fragments.links.LinksFragment
import uddug.com.naukoteka.ui.fragments.media.MediaFragment

class ProfileAdapter(
    private val fm: Fragment
) : FragmentStateAdapter(fm) {

    private val fragments =
        mutableListOf<Fragment>(MediaFragment(), LinksFragment(), FilesFragment(), AudioFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}