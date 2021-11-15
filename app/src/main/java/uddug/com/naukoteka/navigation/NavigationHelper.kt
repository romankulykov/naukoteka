package uddug.com.naukoteka.navigation

import uddug.com.naukoteka.ui.fragments.tabs_holder.TabsHolderFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import toothpick.InjectConstructor
import toothpick.ktp.KTP
import uddug.com.naukoteka.R
import uddug.com.naukoteka.di.DI
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.navigation.Screens.ScreenByTag
import uddug.com.naukoteka.navigation.Screens.Tab
import uddug.com.naukoteka.ui.fragments.TabContainerFragment

@InjectConstructor
class NavigationHelper(val fragmentActivity: FragmentActivity) : INavigationHelper {

    private val supportFragmentManager by lazy { fragmentActivity.supportFragmentManager }
    private val tabsHolderFragment by lazy { supportFragmentManager.findFragmentByTag(Screens.TABS_HOLDER_FRAGMENT) as TabsHolderFragment }
    private val tabsHolderChildFragmentManager by lazy { tabsHolderFragment.childFragmentManager }

    override fun onTabSelect(navBarItem: Screens.BottomNavigationTab, newRoot: Boolean) {
        if (newRoot) {
            getTabRouter(navBarItem)?.router?.backTo(ScreenByTag(navBarItem.tabName))
        } else {
            selectTab(navBarItem)
        }
    }

    private fun getTabRouter(tab: Screens.BottomNavigationTab): TabRouterHolder? {
        return KTP.openRootScope()
            .openSubScope(DI.APP_SCOPE)
            .openSubScope(DI.MAIN_ACTIVITY_SCOPE).openSubScope(tab.tabName)
            .getInstance(TabRouterHolder::class.java)
    }

    private fun selectTab(
        navTab: Screens.BottomNavigationTab,
        overriddenScreenTag: String? = null
    ) {
        val tag = navTab.tabName
        val fm = tabsHolderChildFragmentManager
        var currentFragment: Fragment? = null
        val fragments = fm.fragments
        for (f in fragments) {
            if (f.isVisible) {
                currentFragment = f
                break
            }
        }
        val newFragment = fm.findFragmentByTag(tag)
        if (currentFragment != null && newFragment != null && currentFragment === newFragment) return
        val transaction = fm.beginTransaction()
        if (newFragment == null) {
            transaction.add(
                R.id.container,
                Tab(navTab, overriddenScreenTag).createFragment(fm.fragmentFactory), tag
            )
        }
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }
        if (newFragment != null) {
            transaction.show(newFragment)
            (newFragment as? BaseFragment)?.onFragmentResume()
        }
        transaction.commitNow()

        if (overriddenScreenTag != null) {
            val isNotExistSearchScreenInStack =
                newFragment?.childFragmentManager?.fragments?.find { it.tag == overriddenScreenTag } == null
            if (isNotExistSearchScreenInStack) {
                (newFragment as? TabContainerFragment)?.router?.navigateTo(
                    ScreenByTag(
                        overriddenScreenTag
                    )
                )
            } else {
                //val ft = newFragment!!.childFragmentManager.beginTransaction()
                //ft.show(newFragment)
                //ft.commitNow()
            }

        }
    }

    override fun showPreviousTabIfExist(tabToDestroy: Screens.BottomNavigationTab) {
        val tag = tabToDestroy.tabName
        val fm = tabsHolderChildFragmentManager
        val fragmentContainerToRemove = fm.findFragmentByTag(tag)
        val transaction = fm.beginTransaction()
        if (fragmentContainerToRemove != null) {
            transaction.remove(fragmentContainerToRemove)
            transaction.commitNow()
        }
        val fragments = fm.fragments
        val lastVisibleFragment = fragments.lastOrNull()
        if (lastVisibleFragment != null) {
            val prevTab = (lastVisibleFragment as TabContainerFragment).containerName
            tabsHolderFragment.bottomMenuClick(prevTab)
            selectTab(prevTab)
        } else {
            fragmentActivity.finish()
        }
    }

    override fun bottomMenuVisibility(isVisible: Boolean) {
        tabsHolderFragment.bottomMenuVisibility(isVisible)
    }

    override fun bottomMenuItemSelected(
        tabToSelect: Screens.BottomNavigationTab,
        screenTag: String?
    ) {
        tabsHolderFragment.bottomMenuClick(tabToSelect)
        selectTab(tabToSelect, screenTag)
    }


}