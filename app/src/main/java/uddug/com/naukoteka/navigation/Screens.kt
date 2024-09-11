package uddug.com.naukoteka.navigation

import android.content.Intent
import android.net.Uri
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.ui.fragments.StubFragment
import uddug.com.naukoteka.ui.fragments.TabContainerFragment
import uddug.com.naukoteka.ui.fragments.auth_flow.login.LoginFragment
import uddug.com.naukoteka.ui.fragments.auth_flow.password_recovery.PasswordRecoveryFragment
import uddug.com.naukoteka.ui.fragments.auth_flow.registration_first_step.RegistrationFirstStepFragment
import uddug.com.naukoteka.ui.fragments.auth_flow.registration_second_step.RegistrationSecondStepFragment
import uddug.com.naukoteka.ui.fragments.auth_flow.registration_third_step.RegistrationThirdStepFragment
import uddug.com.naukoteka.ui.fragments.auth_flow.short_info_profile.ShortInfoProfileFragment
import uddug.com.naukoteka.ui.fragments.auth_flow.signed_up.SignedUpFinishedFragment
import uddug.com.naukoteka.ui.fragments.auth_flow.tutorial.TutorialFragment
import uddug.com.naukoteka.ui.fragments.auth_flow.web_view_auth.WebViewAuthFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_add_contact.ChatAddContactFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail.ChatDetailFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.chat_select_contact.ChatSelectContactFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.chats_list_detail.ChatsListDetailFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.create_group.CreateGroupFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.profile.ChatDetailInfoFragment
import uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.SearchInChapterFragment
import uddug.com.naukoteka.ui.fragments.splash.SplashFragment
import uddug.com.naukoteka.ui.fragments.tabs_holder.TabsHolderFragment
import java.io.Serializable


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

    const val TABS_HOLDER_FRAGMENT = "tabsHolderFragment"
    const val CHAT = "chatFragment"
    const val CHAT_DETAIL = "chatDetailFragment"
    const val CHAT_ADD_CONTACT = "chatAddContactFragment"
    const val CHAT_CREATE_GROUP = "chatSelectContactFragment"
    const val CREATE_GROUP = "createGroupFragment"
    const val PROFILE = "profileFragment"
    const val GROUP_SCREEN = "groupScreenFragment"
    const val SEARCH_IN_CHAPTER_SCREEN = "searchInChapterScreenFragment"

    enum class BottomNavigationTab(val tabName: String) : Serializable {
        NAU_SPHERE("sphereTabContainer"),
        NAU_PROFILE("profileTabContainer"),
        NAU_CHAT("chatTabContainer"),
        MY_TERRITORY("territoryTabContainer"),
        NAU_SEARCH("searchTabContainer")
    }

    fun Splash() = FragmentScreen(SPLASH_SCREEN) { SplashFragment() }
    fun Tutorial() = FragmentScreen(TUTORIAL_SCREEN) { TutorialFragment() }

    fun Login() = FragmentScreen(LOGIN) { LoginFragment() }

    fun ChatInfoScreen(chatPreview: ChatPreview) =
        FragmentScreen(GROUP_SCREEN) { ChatDetailInfoFragment.newInstance(chatPreview) }

    fun SearchInChapterScreen(dialogId: Int? = null) =
        FragmentScreen(SEARCH_IN_CHAPTER_SCREEN) { SearchInChapterFragment.newInstance(dialogId) }

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

    fun TabsHolder() = FragmentScreen(TABS_HOLDER_FRAGMENT) { TabsHolderFragment() }

    fun Tab(tabName: BottomNavigationTab, overriddenScreenTag: String? = null) =
        FragmentScreen(tabName.tabName) {
            TabContainerFragment.getNewInstance(tabName, overriddenScreenTag)
        }

    fun Stub() = FragmentScreen { StubFragment() }

    fun NauSphere(tabName: String) = FragmentScreen(tabName) { StubFragment() }
    fun NauProfile(tabName: String) = FragmentScreen(tabName) { StubFragment() }
    fun NauChat(tabName: String) = FragmentScreen(tabName) { ChatsListDetailFragment() }
    fun MyTerritory(tabName: String) = FragmentScreen(tabName) { StubFragment() }
    fun NauSearch(tabName: String) = FragmentScreen(tabName) { StubFragment() }

    fun ChatAddContact() = FragmentScreen(CHAT_ADD_CONTACT) { ChatAddContactFragment() }
    fun ChatCreateGroup() = FragmentScreen(CHAT_CREATE_GROUP) { ChatSelectContactFragment() }
    fun ChatDetail(chat: ChatPreview) =
        FragmentScreen(CHAT_DETAIL) { ChatDetailFragment.newInstance(chat) }

    fun CreateGroup(selectedContacts: List<UserChatPreview>) =
        FragmentScreen(CREATE_GROUP) { CreateGroupFragment.newInstance(selectedContacts) }


    fun ScreenByTag(tag: String?): FragmentScreen {
        return when (tag) {
            /*HOME_SCREEN -> Home(navBarItem.tabName)
            PROFILE_SCREEN -> Profile()*/
            else -> throw UnsupportedOperationException("Can't find fragment by tag via method ScreenByTag")
        }
    }
}
