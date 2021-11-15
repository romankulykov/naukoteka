package uddug.com.naukoteka.navigation

import android.content.Intent
import android.net.Uri
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import uddug.com.naukoteka.ui.fragments.chat_add_contact.ChatAddContactFragment
import uddug.com.naukoteka.ui.fragments.chat_detail.ChatDetailFragment
import uddug.com.naukoteka.ui.fragments.chat_select_contact.ChatSelectContactFragment
import uddug.com.naukoteka.ui.fragments.chats_detail.ChatsDetailFragment
import uddug.com.naukoteka.ui.fragments.tutorial.TutorialFragment
import uddug.com.naukoteka.ui.fragments.login.LoginFragment
import uddug.com.naukoteka.ui.fragments.password_recovery.PasswordRecoveryFragment
import uddug.com.naukoteka.ui.fragments.registration_first_step.RegistrationFirstStepFragment
import uddug.com.naukoteka.ui.fragments.registration_second_step.RegistrationSecondStepFragment
import uddug.com.naukoteka.ui.fragments.registration_third_step.RegistrationThirdStepFragment
import uddug.com.naukoteka.ui.fragments.short_info_profile.ShortInfoProfileFragment
import uddug.com.naukoteka.ui.fragments.signed_up.SignedUpFinishedFragment
import uddug.com.naukoteka.ui.fragments.splash.SplashFragment
import uddug.com.naukoteka.ui.fragments.web_view_auth.WebViewAuthFragment


object Screens {

    const val RESULT_AUTH_SOCIAL = "RESULT_AUTH_SOCIAL"

    const val LOGIN = "loginFragment"
    const val TUTORIAL_SCREEN = "tutorialFragment"
    const val SPLASH_SCREEN = "splashFragment"
    const val REGISTRATION_FIRST_STEP = "registrationFirstStepFragment"
    const val REGISTRATION_SECOND_STEP = "registrationSecondStepFragment"
    const val REGISTRATION_THIRD_STEP = "registrationThirdStepFragment"
    const val WEB_VIEW_AUTH = "webViewAuth"
    const val PASSWORD_RECOVERY = "passwordRecoveryFragment"
    const val SHORT_INFO_PROFILE = "shortInfoProfileFragment"
    const val SIGNED_UP = "signedUpFragment"
    const val CHAT = "chatFragment"
    const val CHAT_DETAIL = "chatDetailFragment"
    const val CHAT_ADD_CONTACT = "chatAddContactFragment"
    const val CHAT_CREATE_GROUP = "chatSelectContactFragment"


    fun Splash() = FragmentScreen(SPLASH_SCREEN) { SplashFragment() }
    fun Tutorial() = FragmentScreen(TUTORIAL_SCREEN) { TutorialFragment() }

    fun Login() = FragmentScreen(LOGIN) { LoginFragment() }

    fun ChatDetail() = FragmentScreen(CHAT_DETAIL) { ChatDetailFragment() }

    fun RegistrationFirstStep() =
        FragmentScreen(REGISTRATION_FIRST_STEP) { RegistrationFirstStepFragment() }

    fun RegistrationSecondStep() =
        FragmentScreen(REGISTRATION_SECOND_STEP) { RegistrationSecondStepFragment() }

    fun RegistrationThirdStep() =
        FragmentScreen(REGISTRATION_THIRD_STEP) { RegistrationThirdStepFragment() }

    fun WebViewAuth(url: String) =
        FragmentScreen(WEB_VIEW_AUTH) { WebViewAuthFragment.newInstance(url) }

    fun OpenBrowser(url: String) = ActivityScreen {
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }

    fun PasswordRecovery() =
        FragmentScreen(PASSWORD_RECOVERY) { PasswordRecoveryFragment() }

    fun ShortInfoProfile() =
        FragmentScreen(SHORT_INFO_PROFILE) { ShortInfoProfileFragment() }

    fun SignedUpFinished() =
        FragmentScreen(SIGNED_UP) { SignedUpFinishedFragment() }

    fun Chat() =
        FragmentScreen(CHAT) { ChatsDetailFragment() }

    fun ChatAddContact() = FragmentScreen(CHAT_ADD_CONTACT) { ChatAddContactFragment() }
    fun ChatCreateGroup() = FragmentScreen(CHAT_CREATE_GROUP) { ChatSelectContactFragment() }
}