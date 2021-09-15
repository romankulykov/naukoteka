package medved.studio.pharmix.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import medved.studio.pharmix.ui.fragments.tutorial.TutorialFragment
import medved.studio.pharmix.ui.fragments.login.LoginFragment
import java.io.Serializable
import medved.studio.pharmix.ui.fragments.splash.SplashFragment


object Screens {


    const val LOGIN = "loginFragment"
    const val TUTORIAL_SCREEN = "tutorialFragment"
    const val SPLASH_SCREEN = "splashFragment"

    fun Splash() = FragmentScreen(SPLASH_SCREEN) { SplashFragment() }
    fun Tutorial() = FragmentScreen(TUTORIAL_SCREEN) { TutorialFragment() }

    fun Login() = FragmentScreen(LOGIN) { LoginFragment() }


}