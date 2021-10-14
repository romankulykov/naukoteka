package medved.studio.pharmix.ui.fragments.login

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import medved.studio.domain.repositories.auth.models.SocialType
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentLoginBinding
import medved.studio.pharmix.ext.data.AR_LOCALE
import medved.studio.pharmix.ext.data.EN_LOCALE
import medved.studio.pharmix.ext.data.RU_LOCALE
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.login.LoginPresenter
import medved.studio.pharmix.presentation.login.LoginView
import medved.studio.pharmix.ui.custom.square_toast.SquareToast
import medved.studio.pharmix.ui.custom.square_toast.ToastInfo
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.*

class LoginFragment : BaseFragment(R.layout.fragment_login), LoginView, BackButtonListener {


    override val contentView by viewBinding(FragmentLoginBinding::bind)

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter {
        return getScope().getInstance(LoginPresenter::class.java)
    }

    var language: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.activity = requireActivity()
        contentView.run {
            tvRegistration.setOnClickListener { presenter.toRegistration() }
            ctiEmail.validFieldFocusListener = { isValid -> showIsValidEmail(isValid) }
            ctiEmail.doAfterTextChange { checkValidFields() }
            ctiPass.doAfterTextChange { checkValidFields() }
            btnEnter.setOnClickListener { presenter.enter(ctiEmail.text(), ctiPass.text()) }
            ibVkontakte.setOnClickListener { presenter.authSocial(SocialType.VK) }
            ibOk.setOnClickListener { presenter.authSocial(SocialType.OK) }
            ibFacebook.setOnClickListener { presenter.authSocial(SocialType.FACEBOOK) }
            ibMail.setOnClickListener { presenter.authSocial(SocialType.MAIL_RU) }
            ibGoogle.setOnClickListener { presenter.authSocial(SocialType.GOOGLE) }
            tvForgetPassword.setOnClickListener { presenter.toPasswordRecovery() }
            ivArrowDown.setOnClickListener {
                showPopupMenu(it)
                ivArrowUp.isVisible = true
                ivArrowDown.isVisible = false
            }
            tvLanguage.setOnClickListener {
                showPopupMenu(it)
                ivArrowUp.isVisible = true
                ivArrowDown.isVisible = false
            }
        }
    }

    override fun onPause() {
        contentView.ctiEmail.validFieldFocusListener = null
        super.onPause()
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

    private fun showIsValidEmail(isValid: Boolean) {
        contentView.tvError.setText(R.string.error_email_format)
        contentView.tvError.isGone = isValid
    }

    override fun showErrorCredentials(flag: Boolean) {
        contentView.tvError.setText(R.string.error_incorrect_credentials)
        contentView.tvError.isVisible = flag
        contentView.btnEnter.isEnabled = !flag
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

    fun showPopupMenu(v: View): PopupWindow = contentView.run {
        var child = LayoutInflater.from(requireContext()).inflate(R.layout.popup_languages, null)
        val popupWindow = PopupWindow(requireContext())
        with(popupWindow) {
            contentView = child
            setBackgroundDrawable(null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                elevation = 12F
            }
            isFocusable = true
            isOutsideTouchable = true
            showAsDropDown(v)
            setOnDismissListener {
                visibilityOfArrows()
                tvLanguage.setTextColor(resources.getColor(R.color.text_inactive))
            }
            tvLanguage.setTextColor(resources.getColor(R.color.white))
            with(child) {

                findViewById<TextView>(R.id.tv_english).apply {
                }.setOnClickListener {
                    presenter.onLanguageChange(EN_LOCALE)
                    language = getString(R.string.english)
                    tvLanguage.setText(language)
                    dismiss()
                    visibilityOfArrows()
                }
                findViewById<TextView>(R.id.tv_arab).apply {
                }.setOnClickListener {
                    presenter.onLanguageChange(AR_LOCALE)
                    language = getString(R.string.arab)
                    tvLanguage.setText(language)
                    dismiss()
                    visibilityOfArrows()
                }
                findViewById<TextView>(R.id.tv_russian).apply {
                }.setOnClickListener {
                    presenter.onLanguageChange(RU_LOCALE)
                    language = getString(R.string.russian)
                    tvLanguage.setText(language)
                    dismiss()
                    visibilityOfArrows()
                }
            }
        }
        return popupWindow
    }

    fun visibilityOfArrows() {
        contentView.run {
            ivArrowDown.isVisible = true
            ivArrowUp.isVisible = false
        }
    }

    override fun showLanguages(locales: List<CustomLanguage>) {
        
    }
}