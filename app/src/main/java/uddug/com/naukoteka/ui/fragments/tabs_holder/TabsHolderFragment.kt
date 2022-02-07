package uddug.com.naukoteka.ui.fragments.tabs_holder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.ktp.delegate.inject
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentTabsHolderBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.global.views.BottomNavigationView
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.INavigationHelper
import uddug.com.naukoteka.navigation.Screens
import uddug.com.naukoteka.presentation.chat_flow.links.LinksPresenter
import uddug.com.naukoteka.presentation.tabs_holder.TabsHolderPresenter
import uddug.com.naukoteka.presentation.tabs_holder.TabsHolderView
import uddug.com.naukoteka.utils.ActivityResultListener
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.RouterProvider
import uddug.com.naukoteka.utils.ui.px
import uddug.com.naukoteka.utils.viewBinding

class TabsHolderFragment : BaseFragment(R.layout.fragment_tabs_holder), BottomNavigationView,
    RouterProvider,
    BackButtonListener, ActivityResultListener, TabsHolderView {

    @InjectPresenter
    lateinit var presenter: TabsHolderPresenter

    @ProvidePresenter
    fun providePresenter(): TabsHolderPresenter {
        return getScope().getInstance(TabsHolderPresenter::class.java)
    }

    override val contentView by viewBinding(FragmentTabsHolderBinding::bind)

    val navigationHelper: INavigationHelper by inject()

    override val router: AppRouter by inject()

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation()
        Handler(Looper.getMainLooper()).postDelayed({
            navigationHelper.onTabSelect(Screens.BottomNavigationTab.NAU_CHAT)
            contentView.vBottom.selectChat()
        }, 500L)
    }

    private fun initBottomNavigation() {
        contentView.vBottom.startClickListeners(navigationHelper)
    }

    override fun changeBadgeCountChat(count: Int) {

    }

    override fun bottomMenuClick(bottomMenuView: Screens.BottomNavigationTab) {
        logger.debug("bottom menu: click ${bottomMenuView.tabName}")
        contentView.vBottom.select(bottomMenuView)
    }

    override fun bottomMenuVisibility(isVisible: Boolean) {
        if (isVisible) {
            contentView.container.setPadding(0, 0, 0, 87.px)
        } else {
            contentView.container.setPadding(0, 0, 0, 0)
        }
        contentView.vBottom.isVisible = isVisible
    }

    override fun onBackPressed(): Boolean {
        val fm = childFragmentManager
        var fragment: Fragment? = null
        val fragments = fm.fragments
        for (fragmentPosition in fragments.size - 1 downTo 0) {
            val currentFragment = fragments[fragmentPosition]
            if (currentFragment.isVisible) {
                fragment = currentFragment
                break
            }
        }
        return if (fragment != null && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            true
        } else {
            router.exit()
            true
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val fm = childFragmentManager
        var fragment: Fragment? = null
        val fragments = fm.fragments
        for (fragmentPosition in fragments.size - 1 downTo 0) {
            val currentFragment = fragments[fragmentPosition]
            if (currentFragment.isVisible) {
                fragment = currentFragment
                break
            }
        }
        if (fragment != null && fragment is ActivityResultListener) {
            (fragment as ActivityResultListener).onFragmentResult(requestCode, resultCode, data)
        }
    }
}