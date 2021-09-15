package medved.studio.pharmix.ui.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import medved.studio.data.cache.first_launch.FirstLaunchCache
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentSplashBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import medved.studio.pharmix.utils.viewBinding
import toothpick.ktp.delegate.inject

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    override val contentView by viewBinding(FragmentSplashBinding::bind)

    private val router: AppRouter by inject()
    private val isFirstLaunched: FirstLaunchCache by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.rootSplash.run {
            Handler(Looper.getMainLooper()).postDelayed({
                if (isFirstLaunched.entity) {
                    router.replaceScreen(Screens.Tutorial())
                } else {
                    router.replaceScreen(Screens.Login())
                }
            }, 1000L)
        }
    }

}