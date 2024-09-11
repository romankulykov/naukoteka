package uddug.com.naukoteka.ui.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import uddug.com.data.cache.first_launch.FirstLaunchCache
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentSplashBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens
import uddug.com.naukoteka.utils.viewBinding
import toothpick.ktp.delegate.inject
import uddug.com.data.cache.token.UserTokenCache

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    override val contentView by viewBinding(FragmentSplashBinding::bind)

    private val router: AppRouter by inject()
    private val isFirstLaunched: FirstLaunchCache by inject()
    private val userTokenCache: UserTokenCache by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.rootSplash.run {
            Handler(Looper.getMainLooper()).postDelayed({
                if (isFirstLaunched.entity) {
                    router.newRootScreen(Screens.Tutorial())
                } else {
                    if (userTokenCache.entity != null) {
                        router.newRootScreen(Screens.TabsHolder())
                    } else {
                        router.newRootScreen(Screens.Login())
                    }
                }
            }, 1000L)
        }
    }

}