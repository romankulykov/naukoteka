package medved.studio.pharmix.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import medved.studio.pharmix.databinding.ActivityMainBinding
import medved.studio.pharmix.global.base.BaseActivity
import medved.studio.pharmix.utils.ActivityResultListener
import medved.studio.pharmix.utils.viewBinding

class DeepLinkActivity : BaseActivity() {

    override val contentView by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView.root)

        handleDeepLink()

    }

    private fun handleDeepLink(newIntent: Intent? = null) {
        val intent = newIntent ?: intent
        val keyAuthorization = intent.data?.getQueryParameter("key")
        if (!keyAuthorization.isNullOrEmpty()) {
            startActivity(Intent(this, MainActivity::class.java).apply {
                putExtra(IntentKeys.Registration.KEY, IntentKeys.Registration(keyAuthorization))
            })
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
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


}