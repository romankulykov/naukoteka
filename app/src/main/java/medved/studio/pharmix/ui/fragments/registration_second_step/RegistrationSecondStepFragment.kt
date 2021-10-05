package medved.studio.pharmix.ui.fragments.registration_second_step

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentRegistrationSecondStepBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.registration_second_step.RegistrationSecondStepPresenter
import medved.studio.pharmix.presentation.registration_second_step.RegistrationSecondStepView
import medved.studio.pharmix.ui.AppConfigs
import medved.studio.pharmix.ui.adapters.password_requirements.PasswordRequirementsAdapter
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class RegistrationSecondStepFragment : BaseFragment(R.layout.fragment_registration_second_step),
    RegistrationSecondStepView, BackButtonListener {

    override val contentView by viewBinding(FragmentRegistrationSecondStepBinding::bind)

    @InjectPresenter
    lateinit var presenter: RegistrationSecondStepPresenter

    @ProvidePresenter
    fun providePresenter(): RegistrationSecondStepPresenter {
        return getScope().getInstance(RegistrationSecondStepPresenter::class.java)
    }

    private var counterEnterTestPassword = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            ivBack.setOnClickListener { onBackPressed() }
            ctiPassConfirmation.onFocusChange { hasFocus ->
                if (hasFocus) {
                    startListenPasswords()
                }
            }
            tvSecondStep.setOnClickListener {
                counterEnterTestPassword++
                if (counterEnterTestPassword % 4 == 0) {
                    ctiPass.setText("1Q2w#_asd")
                    ctiPassConfirmation.setText("1Q2w#_asd")
                    presenter.nextStep(ctiPass.text())
                }
            }
            tvPasswordRequirements.setOnClickListener { showDialogRequirements() }
        }
    }

    private fun showDialogRequirements() {
        val dialogView = layoutInflater.inflate(
            R.layout.dialog_password_requirements,
            ConstraintLayout(requireContext())
        )
        AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setCancelable(true)
            .setView(dialogView)
            .create().apply {
                dialogView.findViewById<RecyclerView>(R.id.rv_requirements)?.adapter =
                    PasswordRequirementsAdapter().apply { setItems(AppConfigs.getPasswordRequirements()) }
                dialogView.findViewById<TextView>(R.id.btn_accessibly)
                    ?.setOnClickListener { dismiss() }
            }.show()
    }

    private fun startListenPasswords() {
        contentView.run {
            ctiPass.doAfterTextChange { checkValidFields() }
            ctiPassConfirmation.doAfterTextChange { checkValidFields() }
            btnRegistrationNext.setOnClickListener { presenter.nextStep(ctiPass.text()) }
        }
    }

    private fun checkValidFields() {
        contentView.run {
            val password = ctiPass.text()
            val passwordConfirmation = ctiPassConfirmation.text()
            presenter.isValidFields(password, passwordConfirmation)
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun showButtonState(isEnabled: Boolean) {
        contentView.run {
            btnRegistrationNext.isEnabled = isEnabled
            tvError.isGone = isEnabled
            ctiPass.showError(!isEnabled)
            ctiPassConfirmation.showError(!isEnabled)
        }
    }
}