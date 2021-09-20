package medved.studio.pharmix.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import medved.studio.pharmix.R

class AnimatableAppNavigator(
    activity: FragmentActivity,
    container: Int,
    fragmentManager: FragmentManager = activity.supportFragmentManager
) :
    AppNavigator(activity, container, fragmentManager) {
    override fun setupFragmentTransaction(
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment?
    ) {
        super.setupFragmentTransaction(fragmentTransaction.apply {
            setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
        }, currentFragment, nextFragment)
    }
}