package medved.studio.pharmix.ui.fragments.registration_second_step

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentRegistrationSecondStepBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.registration_second_step.RegistrationSecondStepPresenter
import medved.studio.pharmix.presentation.registration_second_step.RegistrationSecondStepView
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            ivBack.setOnClickListener { onBackPressed() }
            ctiPassConfirmation.onFocusChange { hasFocus ->
                if (hasFocus) {
                    startListenPasswords()
                }
            }
        }
    }

    private fun startListenPasswords() {
        contentView.run {
            ctiPass.doAfterTextChange { checkValidFields() }
            ctiPassConfirmation.doAfterTextChange { checkValidFields() }
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