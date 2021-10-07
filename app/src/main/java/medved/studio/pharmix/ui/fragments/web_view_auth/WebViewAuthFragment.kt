package medved.studio.pharmix.ui.fragments.web_view_auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Message
import android.view.View
import android.webkit.*
import androidx.core.os.bundleOf
import com.github.terrakok.cicerone.Router
import medved.studio.domain.repositories.auth.models.SocialType
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentWebViewBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import medved.studio.pharmix.ui.AppConfigs
import medved.studio.pharmix.ui.IntentKeys
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import toothpick.ktp.delegate.inject

class WebViewAuthFragment : BaseFragment(R.layout.fragment_web_view), BackButtonListener {

    companion object {

        private const val URL_KEY = "WebViewAuthFragment.URL_KEY"

        fun newInstance(url: String) = WebViewAuthFragment().apply {
            arguments = bundleOf(URL_KEY to url)
        }
    }

    private val router: AppRouter by inject()

    private val requiredUrl get() = arguments?.getString(URL_KEY)

    override val contentView by viewBinding(FragmentWebViewBinding::bind)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clearCookies()
        contentView.run {
            webView.run {
                loadUrl(requiredUrl!!)
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.setSupportMultipleWindows(true)
                setAcceptThirdPartyCookies(this)
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        if (request?.url.toString().contains(AppConfigs.SOCIAL_LOGIN_ENDPOINT)) {
                            val key = request?.url?.getQueryParameter("key")
                            val socialTypeRaw = request?.url?.getQueryParameter("social_type")
                            val socialType = SocialType.values().find { it.raw == socialTypeRaw }
                            if (key != null && socialType != null) {
                                router.sendResult(
                                    Screens.RESULT_AUTH_SOCIAL,
                                    IntentKeys.SocialAuthorization(key, socialType)
                                )
                                onBackPressed()
                            }
                            return false
                        }
                        request?.url
                        return super.shouldOverrideUrlLoading(view, request)
                    }

                    override fun shouldInterceptRequest(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): WebResourceResponse? {
                        request?.url
                        return super.shouldInterceptRequest(view, request)
                    }
                }
                webChromeClient = object : WebChromeClient() {
                    override fun onCreateWindow(
                        view: WebView?,
                        isDialog: Boolean,
                        isUserGesture: Boolean,
                        resultMsg: Message?
                    ): Boolean {
                        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
                    }
                }
            }
        }
    }

    private fun setAcceptThirdPartyCookies(webView: WebView) {
        val cookieManager = CookieManager.getInstance()

        // This is a safeguard, in case you've disabled cookies elsewhere
        if (!cookieManager.acceptCookie()) {
            cookieManager.setAcceptCookie(true)
        }
        cookieManager.setAcceptThirdPartyCookies(webView, true)
    }


    private fun clearCookies() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookies(null)
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

}