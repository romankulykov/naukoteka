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
    private val fm: FragmentManager, private val context: Context
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titles = arrayListOf(
        R.string.media,
        R.string.links,
        R.string.files,
        R.string.audio,
    )

    private val fragments =
        mutableListOf<Fragment>(MediaFragment(), LinksFragment(), FilesFragment(), AudioFragment())

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(titles[position])

    }

    override fun getItem(position: Int): Fragment = fragments[position]
}