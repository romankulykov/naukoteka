package medved.studio.pharmix.ui.fragments.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.text.parseAsHtml
import io.reactivex.Observable
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentLoginBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.login.LoginPresenter
import medved.studio.pharmix.presentation.login.LoginView
import medved.studio.pharmix.utils.ActivityResultListener
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.ui.vibrateShort
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.concurrent.TimeUnit

class LoginFragment : BaseFragment(R.layout.fragment_login), LoginView, BackButtonListener,
    ActivityResultListener {


    override val contentView by viewBinding(FragmentLoginBinding::bind)

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter {
        return getScope().getInstance(LoginPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            tvRegistration.text = context?.getString(R.string.login_registration)?.parseAsHtml()
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun onBackPressed(): Boolean {
        return true
    }


}