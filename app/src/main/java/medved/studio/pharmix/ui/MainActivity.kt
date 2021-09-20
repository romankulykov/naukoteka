package medved.studio.pharmix.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.ActivityMainBinding
import medved.studio.pharmix.global.base.BaseActivity
import medved.studio.pharmix.global.views.InformativeView
import medved.studio.pharmix.global.views.LoadingView
import medved.studio.pharmix.navigation.AnimatableAppNavigator
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import medved.studio.pharmix.navigation.Screens.Splash
import medved.studio.pharmix.ui.custom.SquareToast
import medved.studio.pharmix.utils.ActivityResultListener
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.RouterProvider
import medved.studio.pharmix.utils.viewBinding
import toothpick.ktp.delegate.inject

class MainActivity : BaseActivity(), RouterProvider, LoadingView, InformativeView {

    override val contentView by viewBinding(ActivityMainBinding::inflate)

    private val navigatorHolder: NavigatorHolder by inject()

    private val navigator: Navigator by lazy { AnimatableAppNavigator(this, R.id.container) }

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
        }

        LayoutInflater.from(this).inflate(R.layout.progress_bar, contentView.container, true)
            .also { view ->
                this.progressBar = view.findViewById(R.id.progressBar) as ProgressBar
            }
    }

    private fun handleDeepLink(newIntent: Intent) {
        newIntent.getParcelableExtra<IntentKeys.Registration>(IntentKeys.Registration.KEY)
            ?.let { registration ->
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

    override fun showInfoMessage(message: String?) {
        if (message.isNullOrBlank()) return
        SquareToast.getInstance(this).show(message)
    }

    override fun showInfoMessage(message: Int?) {
        if (message == null) return
        showInfoMessage(getString(message))
    }

    override fun showActionMessage(message: String?, action: String, invoker: () -> Unit) {
        if (message.isNullOrBlank()) return
        SquareToast.getInstance(this).show(message, action, invoker)
    }

    override fun showActionMessage(message: Int?, action: Int, invoker: () -> Unit) {
        if (message == null) return
        showActionMessage(getString(message), getString(action), invoker)
    }

    override fun showIconableMessage(message: Int?, drawableResId: Int) {
        if (message == null) return
        showIconableMessage(getString(message), drawableResId)
    }

    override fun showIconableMessage(message: String?, drawableResId: Int) {
        if (message.isNullOrBlank()) return
        SquareToast.getInstance(this).showIconable(message, drawableResId)
    }

}