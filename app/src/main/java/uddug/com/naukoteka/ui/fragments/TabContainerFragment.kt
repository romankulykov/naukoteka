package uddug.com.naukoteka.ui.fragments

import android.content.Intent
import android.os.Bundle
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import uddug.com.domain.interactors.account.SessionInteractor
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentTabContainerBinding
import uddug.com.naukoteka.di.modules.FlowNavigationModule
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.navigation.AnimatableAppNavigator
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens
import uddug.com.naukoteka.navigation.TabCiceroneHolder
import uddug.com.naukoteka.utils.ActivityResultListener
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.RouterProvider
import uddug.com.naukoteka.utils.viewBinding

class TabContainerFragment : BaseFragment(R.layout.fragment_tab_container), RouterProvider,
    BackButtonListener, ActivityResultListener {

    override val contentView by viewBinding(FragmentTabContainerBinding::bind)

    private val navigator: Navigator by lazy {
        AnimatableAppNavigator(
            activity!!,
            R.id.ftc_container,
            childFragmentManager
        )
    }

    val ciceroneHolder: TabCiceroneHolder by inject()
    val sessionInteractor: SessionInteractor by inject()

    val containerName: Screens.BottomNavigationTab
        get() = arguments!!.getSerializable(TAB_CONTAINER_NAME) as Screens.BottomNavigationTab

    private val overriddenScreenTag get() = arguments?.getString(TAB_FIRST_SCREEN)

    private val cicerone: Cicerone<AppRouter>
        get() = ciceroneHolder.getCicerone(containerName.tabName)

    override val router: AppRouter
        get() = cicerone.router

    init {
        getScope()
            .inject(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        getScope()
            .openSubScope(containerName.tabName)
            .installModules(FlowNavigationModule(router))
        super.onCreate(savedInstanceState)
        sessionInteractor.getSessionExpirationObservable()
            .subscribe {
                router.replaceScreen(Screens.Login())
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        KTP.closeScope(containerName.tabName)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fragment = childFragmentManager.findFragmentById(R.id.ftc_container)
        if (fragment == null) {
            val replaceScreen = if (overriddenScreenTag != null) {
                Screens.ScreenByTag(overriddenScreenTag)
            } else {
                when (containerName) {
                    Screens.BottomNavigationTab.NAU_SPHERE -> Screens.NauSphere(containerName.tabName)
                    Screens.BottomNavigationTab.NAU_PROFILE -> Screens.NauProfile(containerName.tabName)
                    Screens.BottomNavigationTab.NAU_CHAT -> Screens.NauChat(containerName.tabName)
                    Screens.BottomNavigationTab.MY_TERRITORY -> Screens.MyTerritory(containerName.tabName)
                    Screens.BottomNavigationTab.NAU_SEARCH -> Screens.NauSearch(containerName.tabName)
                }
            }
            cicerone.router.replaceScreen(replaceScreen)
        }
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.ftc_container)
        return if (fragment != null && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            true
        } else {
            if (ciceroneHolder.isTherePreviousTab()) {
                ciceroneHolder.showLastTab(containerName)
            } else {
                cicerone.router.exit()
            }
            true
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val fragment = childFragmentManager.findFragmentById(R.id.ftc_container)
        if (fragment != null && fragment is ActivityResultListener) {
            (fragment as ActivityResultListener).onFragmentResult(requestCode, resultCode, data)
        }
    }

    companion object {
        const val TAB_CONTAINER_NAME = "tab_extra_name"
        const val TAB_FIRST_SCREEN = "tcf_extra_screen"

        fun getNewInstance(name: Screens.BottomNavigationTab, screen: String?) =
            TabContainerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TAB_CONTAINER_NAME, name)
                    putString(TAB_FIRST_SCREEN, screen)
                }
            }
    }
}