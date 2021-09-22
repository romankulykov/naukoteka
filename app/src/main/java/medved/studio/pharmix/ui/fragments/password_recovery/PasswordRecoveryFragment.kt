package medved.studio.pharmix.ui.fragments.password_recovery

import android.os.Bundle
import android.view.View
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentPasswordRecoveryBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.password_recovery.PasswordRecoveryPresenter
import medved.studio.pharmix.presentation.password_recovery.PasswordRecoveryView
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class PasswordRecoveryFragment : BaseFragment(R.layout.fragment_password_recovery),
    PasswordRecoveryView, BackButtonListener {

    private val FLIPPER_PASSWORD_RECOVERY = 0
    private val FLIPPER_PASSWORD_RECOVERY_VERIFICATION = 1
    private val FLIPPER_PASSWORD_RECOVERY_NEW_PASSWORD = 2

    override val contentView by viewBinding(FragmentPasswordRecoveryBinding::bind)

    @InjectPresenter
    lateinit var presenter: PasswordRecoveryPresenter

    @ProvidePresenter
    fun providePresenter(): PasswordRecoveryPresenter {
        return getScope().getInstance(PasswordRecoveryPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            ivBack.setOnClickListener { onBackPressed() }
            viewFlipper.displayedChild = FLIPPER_PASSWORD_RECOVERY
            passwordRecovery.run {
                btnSend.setOnClickListener {
                    viewFlipper.displayedChild = FLIPPER_PASSWORD_RECOVERY_VERIFICATION
                }
                ctiEmail.doAfterTextChange { checkValidField() }
            }
            passwordRecoveryNewPassword.run {
                ctiNewPass.doAfterTextChange { checkValidFields() }
                ctiNewPassConfirmation.doAfterTextChange { checkValidFields() }
            }
            passwordRecoveryVerification.run {
                presenter.startTimerResendCode()
                tvSendAgain.setOnClickListener {
                    viewFlipper.displayedChild = FLIPPER_PASSWORD_RECOVERY_NEW_PASSWORD
                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    private fun checkValidField() {
        contentView.passwordRecovery.run {
            val email = ctiEmail.text()
            presenter.isValidField(email)
        }
    }

    private fun checkValidFields() {
        contentView.passwordRecoveryNewPassword.run {
            val password = ctiNewPass.text()
            val passwordConfirmation = ctiNewPassConfirmation.text()
            presenter.isValidFields(password, passwordConfirmation)
        }
    }

    override fun showButtonState(isEnabled: Boolean) {
        contentView.run {
            passwordRecovery.btnSend.isEnabled = isEnabled
            passwordRecoveryNewPassword.btnSavePassword.isEnabled = isEnabled
        }
    }

    override fun showSecondsToResend(seconds: Int) {
        if (seconds > 0) {
            val minutes = seconds / 60
            val seconds = seconds - (minutes * 60)
            val secondsLeft = String.format("%02d:%02d", minutes, seconds)
            contentView.passwordRecoveryVerification.run {
                tvSendAgain.isVisible = true
                tvSendAgain.text =
                    getString(
                        R.string.password_recovery_verification_send_again,
                        secondsLeft
                    ).parseAsHtml()
            }
        } else if (seconds == 0) {
            val secondsNull = String.format("%02d:%02d", 0, 0)
            contentView.passwordRecoveryVerification.run {
                tvSendAgain.text =
                    getString(
                        R.string.password_recovery_verification_send_again,
                        secondsNull
                    ).parseAsHtml()
            }
        }
    }
}