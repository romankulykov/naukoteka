package medved.studio.pharmix.ui.fragments.password_recovery

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import medved.studio.domain.entities.PasswordRequirementsEntity
import medved.studio.domain.entities.TutorialEntity
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentPasswordRecoveryBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.password_recovery.PasswordRecoveryPresenter
import medved.studio.pharmix.presentation.password_recovery.PasswordRecoveryView
import medved.studio.pharmix.ui.adapters.password_requirements.PasswordRequirementsAdapter
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class PasswordRecoveryFragment : BaseFragment(R.layout.fragment_password_recovery),
    PasswordRecoveryView, BackButtonListener {

    private val FLIPPER_PASSWORD_RECOVERY = 0
    private val FLIPPER_PASSWORD_RECOVERY_VERIFICATION = 1
    private val FLIPPER_PASSWORD_RECOVERY_NEW_PASSWORD = 2
    private lateinit var requirements: MutableList<PasswordRequirementsEntity>
    private var dialog: AlertDialog? = null

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
                btnSend.setOnClickListener { presenter.recoveryPassword(ctiEmail.text()) }
                ctiEmail.doAfterTextChange { checkValidField() }
            }
            passwordRecoveryNewPassword.run {
                ctiNewPass.doAfterTextChange { checkValidFields() }
                ctiNewPassConfirmation.doAfterTextChange { checkValidFields() }
                tvRequirements.setOnClickListener { showDialogRequirements() }
                btnSavePassword.setOnClickListener { presenter.changePassword(ctiNewPass.text()) }
            }
        }
    }

    override fun showTimer() {
        presenter.startTimerResendCode()
        contentView.viewFlipper.displayedChild = FLIPPER_PASSWORD_RECOVERY_VERIFICATION
    }

    override fun onBackPressed(): Boolean {
        contentView.run {
            when (viewFlipper.displayedChild) {
                FLIPPER_PASSWORD_RECOVERY -> presenter.exit()
                FLIPPER_PASSWORD_RECOVERY_NEW_PASSWORD -> viewFlipper.displayedChild =
                    FLIPPER_PASSWORD_RECOVERY
                FLIPPER_PASSWORD_RECOVERY_VERIFICATION ->
                    startDialogCancelPasswordRecovery()
            }
        }
        return true
    }

    private fun checkValidField() {
        contentView.passwordRecovery.run {
            val email = ctiEmail.text()
            presenter.isValidEmail(email)
        }
    }

    private fun checkValidFields() {
        contentView.passwordRecoveryNewPassword.run {
            val password = ctiNewPass.text()
            val passwordConfirmation = ctiNewPassConfirmation.text()
            presenter.isValidPasswords(password, passwordConfirmation)
        }
    }

    override fun showButtonState(isEnabled: Boolean) {
        contentView.passwordRecovery.btnSend.isEnabled = isEnabled
    }

    override fun showButtonStateOfPasswordRecoveryVerification(isEnabled: Boolean) {
        contentView.passwordRecoveryNewPassword.run {
            btnSavePassword.isEnabled = isEnabled
            ctiNewPass.showError(!isEnabled)
            ctiNewPassConfirmation.showError(!isEnabled)
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

    fun checkTokenToRecovery(key: String) {
        presenter.checkKey(key)
    }

    override fun showInputNewPassword() {
        contentView.viewFlipper.displayedChild = FLIPPER_PASSWORD_RECOVERY_NEW_PASSWORD
    }

    private fun showDialogRequirements() {
        val dialogView = layoutInflater.inflate(
            R.layout.dialog_password_requirements,
            ConstraintLayout(requireContext())
        )
        requirements = ArrayList()
        requirements.add(PasswordRequirementsEntity(R.string.dialog_password_requirements_count_of_symbols))
        requirements.add(PasswordRequirementsEntity(R.string.dialog_password_requirements_capital_letters))
        requirements.add(PasswordRequirementsEntity(R.string.dialog_password_requirements_digits))
        requirements.add(PasswordRequirementsEntity(R.string.dialog_password_requirements_symbols))
        AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setCancelable(true)
            .setView(dialogView)
            .create().apply {
                dialogView.findViewById<RecyclerView>(R.id.rv_requirements)?.adapter =
                    PasswordRequirementsAdapter().apply { setItems(requirements) }
                dialogView.findViewById<TextView>(R.id.btn_accessibly)
                    ?.setOnClickListener { dismiss() }
            }.show()
    }

    private fun startDialogCancelPasswordRecovery() {
        val dialogView = layoutInflater.inflate(
            R.layout.dialog_cancel_password_recovery,
            ConstraintLayout(requireContext())
        )
        AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setCancelable(true)
            .setView(dialogView)
            .create().apply {
                dialogView.findViewById<TextView>(R.id.tv_are_you_sure_interrupt_password_recovery)
                    ?.setText(R.string.are_you_sure_want_to_interrupt_the_password_recovery)
                dialogView.findViewById<TextView>(R.id.btn_yes)?.setOnClickListener {
                    presenter.toAuthorization()
                    dismiss()
                }
                dialogView.findViewById<TextView>(R.id.btn_no)?.setOnClickListener { dismiss() }
            }.show()
    }

    override fun startDialogPasswordRecoverySuccessful() {
        val dialogView = layoutInflater.inflate(
            R.layout.dialog_successful_password_recovery,
            ConstraintLayout(requireContext())
        )
        dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setCancelable(true)
            .setView(dialogView)
            .create()
        dialog.apply {
            dialogView.findViewById<ImageView>(R.id.iv_success_password_recovery)
                .setImageResource(R.drawable.ic_success_password_recovery)
            dialogView.findViewById<TextView>(R.id.tv_new_password_save)
                ?.setText(R.string.dialog_password_recovery_new_password_save)
            dialogView.findViewById<TextView>(R.id.tv_password_recovery_successful)
                ?.setText(R.string.dialog_password_recovery_successful)
        }?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
        dialog = null
    }
}