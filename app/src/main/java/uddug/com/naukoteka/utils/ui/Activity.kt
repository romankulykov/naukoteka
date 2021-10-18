package uddug.com.naukoteka.utils.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun <F : Fragment> FragmentActivity.findFragment(tag: String): F? {
    return (supportFragmentManager.findFragmentByTag(tag) as? F)
}