package medved.studio.pharmix.ui.fragments.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import medved.studio.domain.repositories.auth.models.SocialType
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentLoginBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.login.LoginPresenter
import medved.studio.pharmix.presentation.login.LoginView
import medved.studio.pharmix.ui.custom.square_toast.SquareToast
import medved.studio.pharmix.ui.custom.square_toast.ToastInfo
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
            ibVkontakte.setOnClickListener { presenter.authSocial(SocialType.VK) }
            ibOk.setOnClickListener { presenter.authSocial(SocialType.OK) }
            ibFacebook.setOnClickListener { presenter.authSocial(SocialType.FACEBOOK) }
            ibMail.setOnClickListener { presenter.authSocial(SocialType.MAIL_RU) }
            ibGoogle.setOnClickListener { presenter.authSocial(SocialType.GOOGLE) }
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

    override fun showSuccessLogin() {
        showMessage(
            ToastInfo(
                text = getString(R.string.enter_has_successfully_passed),
                leftIcon = R.drawable.ic_check_success,
                rightIcon = R.drawable.ic_cross,
                type = SquareToast.Type.SUCCESS
            )
        )
    }

    override fun showSocialTypes(socialTypes: List<SocialType>) {
        contentView.run {
            ibVkontakte.isVisible = socialTypes.contains(SocialType.VK)
            ibOk.isVisible = socialTypes.contains(SocialType.OK)
            ibFacebook.isVisible = socialTypes.contains(SocialType.FACEBOOK)
            ibMail.isVisible = socialTypes.contains(SocialType.MAIL_RU)
            ibGoogle.isVisible = socialTypes.contains(SocialType.GOOGLE)
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }


}