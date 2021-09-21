package medved.studio.pharmix.navigation

import android.content.Intent
import android.net.Uri
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import medved.studio.pharmix.ui.fragments.tutorial.TutorialFragment
import medved.studio.pharmix.ui.fragments.login.LoginFragment
import medved.studio.pharmix.ui.fragments.registration_first_step.RegistrationFirstStepFragment
import medved.studio.pharmix.ui.fragments.registration_second_step.RegistrationSecondStepFragment
import medved.studio.pharmix.ui.fragments.registration_third_step.RegistrationThirdStepFragment
import java.io.Serializable
import medved.studio.pharmix.ui.fragments.splash.SplashFragment
import medved.studio.pharmix.ui.fragments.web_view_auth.WebViewAuthFragment


object Screens {

    const val RESULT_AUTH_SOCIAL = "RESULT_AUTH_SOCIAL"

    const val LOGIN = "loginFragment"
    const val TUTORIAL_SCREEN = "tutorialFragment"
    const val SPLASH_SCREEN = "splashFragment"
    const val REGISTRATION_FIRST_STEP = "registrationFirstStepFragment"
    const val REGISTRATION_SECOND_STEP = "registrationSecondStepFragment"
    const val REGISTRATION_THIRD_STEP = "registrationThirdStepFragment"
    const val WEB_VIEW_AUTH = "webViewAuth"

    fun Splash() = FragmentScreen(SPLASH_SCREEN) { SplashFragment() }
    fun Tutorial() = FragmentScreen(TUTORIAL_SCREEN) { TutorialFragment() }

    fun Login() = FragmentScreen(LOGIN) { LoginFragment() }

    fun RegistrationFirstStep() =
        FragmentScreen(REGISTRATION_FIRST_STEP) { RegistrationFirstStepFragment() }

    fun RegistrationSecondStep() =
        FragmentScreen(REGISTRATION_SECOND_STEP) { RegistrationSecondStepFragment() }

    fun RegistrationThirdStep() =
        FragmentScreen(REGISTRATION_THIRD_STEP) { RegistrationThirdStepFragment() }

    fun WebViewAuth(url : String) =
        FragmentScreen(WEB_VIEW_AUTH) { WebViewAuthFragment.newInstance(url) }

    fun OpenBrowser(url: String) = ActivityScreen {
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }


}