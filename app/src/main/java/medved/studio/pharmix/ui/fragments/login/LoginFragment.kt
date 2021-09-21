package medved.studio.pharmix.ui.fragments.login

import android.os.Bundle
import android.view.View
import androidx.core.text.parseAsHtml
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentLoginBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.login.LoginPresenter
import medved.studio.pharmix.presentation.login.LoginView
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class LoginFragment : BaseFragment(R.layout.fragment_login), LoginView, BackButtonListener {


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
            tvRegistration.setOnClickListener { presenter.toRegistration() }
            ctiEmail.doAfterTextChange { checkValidFields() }
            ctiPass.doAfterTextChange { checkValidFields() }
            btnEnter.setOnClickListener { presenter.enter(ctiEmail.text(), ctiPass.text()) }
            tvForgetPassword.setOnClickListener { presenter.toPasswordRecovery() }
        }
    }

    private fun checkValidFields() {
        contentView.run {
            val email = ctiEmail.text()
            val password = ctiPass.text()
            presenter.isValidFields(email, password)
        }
    }

    override fun showButtonState(isEnabled: Boolean) {
        contentView.btnEnter.isEnabled = isEnabled
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }


}