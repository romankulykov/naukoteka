package medved.studio.pharmix.ui.fragments.registration_second_step

import android.os.Bundle
import android.view.View
import androidx.core.text.parseAsHtml
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
            ctiPass.doAfterTextChange { checkValidFields() }
            ctiPassConfirmation.doAfterTextChange { checkValidFields() }
            btnRegistrationNext.setOnClickListener { presenter.nextStep() }
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
        contentView.btnRegistrationNext.isEnabled = isEnabled
    }
}