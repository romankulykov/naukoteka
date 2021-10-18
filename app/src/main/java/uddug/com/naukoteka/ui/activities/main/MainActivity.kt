package uddug.com.naukoteka.ui.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ActivityMainBinding
import uddug.com.naukoteka.global.base.BaseActivity
import uddug.com.naukoteka.navigation.AnimatableAppNavigator
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens
import uddug.com.naukoteka.navigation.Screens.Splash
import uddug.com.naukoteka.presentation.main.MainPresenter
import uddug.com.naukoteka.presentation.main.MainView
import uddug.com.naukoteka.ui.IntentKeys
import uddug.com.naukoteka.ui.custom.square_toast.SquareToast
import uddug.com.naukoteka.ui.custom.square_toast.ToastAction
import uddug.com.naukoteka.ui.custom.square_toast.ToastInfo
import uddug.com.naukoteka.ui.fragments.password_recovery.PasswordRecoveryFragment
import uddug.com.naukoteka.ui.fragments.registration_third_step.RegistrationThirdStepFragment
import uddug.com.naukoteka.utils.ActivityResultListener
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.RouterProvider
import uddug.com.naukoteka.utils.ui.findFragment
import uddug.com.naukoteka.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.ktp.delegate.inject

class MainActivity : BaseActivity(), RouterProvider, MainView {

    override val contentView by viewBinding(ActivityMainBinding::inflate)

    private val navigatorHolder: NavigatorHolder by inject()

    private val navigator: Navigator by lazy { AnimatableAppNavigator(this, R.id.container) }

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        return getScope().getInstance(MainPresenter::class.java)
    }

    protected lateinit var progressBar: ProgressBar

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override val router: AppRouter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView.root)
        val isLaunchedAfterSystemKilled = savedInstanceState != null
        if (!isLaunchedAfterSystemKilled) {
            router.navigateTo(Splash())
            //router.navigateTo(RegistrationFirstStep())
            //router.navigateTo(ShortInfoProfile())
        }

        LayoutInflater.from(this).inflate(R.layout.progress_bar, contentView.container, true)
            .also { view ->
                this.progressBar = view.findViewById(R.id.progressBar) as ProgressBar
            }
    }

    private fun handleDeepLink(newIntent: Intent) {
        newIntent.getParcelableExtra<IntentKeys.Registration>(IntentKeys.Registration.KEY)
            ?.let { registration ->
                findFragment<RegistrationThirdStepFragment>(Screens.REGISTRATION_THIRD_STEP)
                    ?.checkTokenToRegistration(registration.key)
            }
        newIntent.getParcelableExtra<IntentKeys.RecoveryPassword>(IntentKeys.RecoveryPassword.KEY)
            ?.let { recovery ->
                findFragment<PasswordRecoveryFragment>(Screens.PASSWORD_RECOVERY)
                    ?.checkTokenToRecovery(recovery.key)
            }
        newIntent.getParcelableExtra<IntentKeys.SocialAuthorization>(IntentKeys.SocialAuthorization.KEY)
            ?.let { socialAuth ->
                presenter.socialAuth(socialAuth.socialType, socialAuth.key)
                // todo pass registration key
                // router.navigateTo(Screens.EndRegistartion())
            }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        var fragment: Fragment? = null
        val fragments = fm.fragments
        for (f in fragments) {
            if (f.isVisible) {
                fragment = f
                break
            }
        }
        if (fragment != null && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return
        } else {
            router.exit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val fm = supportFragmentManager
        var fragment: Fragment? = null
        val fragments = fm.fragments
        for (f in fragments) {
            if (f.isVisible) {
                fragment = f
                break
            }
        }
        if (fragment != null && fragment is ActivityResultListener) {
            (fragment as ActivityResultListener).onFragmentResult(requestCode, resultCode, data)
        }
    }

    override fun showLoading(show: Boolean) {
        runOnUiThread {
            progressBar.isVisible = show
        }
    }

    override fun showRefreshLoading(show: Boolean) {

    }

    override fun showInfoMessage(message: Int?) {
        if (message == null) return
        showInfoMessage(getString(message))
    }

    override fun showInfoMessage(message: String?) {
        if (message.isNullOrBlank()) return
        SquareToast.getInstance(this).show(ToastInfo(text = message))
    }

    override fun showActionMessage(message: Int?, action: Int, invoker: () -> Unit) {
        if (message == null) return
        showActionMessage(getString(message), getString(action), invoker)
    }

    override fun showActionMessage(message: String?, action: String, invoker: () -> Unit) {
        if (message.isNullOrBlank()) return
        SquareToast.getInstance(this).show(
            ToastInfo(
                text = message,
                action = ToastAction(action, invoker)
            )
        )
    }

    override fun showMessage(data: ToastInfo) {
        SquareToast.getInstance(this).show(data)
    }

}