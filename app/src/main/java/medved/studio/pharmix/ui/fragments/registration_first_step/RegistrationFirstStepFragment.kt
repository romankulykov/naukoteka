package medved.studio.pharmix.ui.fragments.registration_first_step

import android.os.Bundle
import android.view.View
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentRegistrationFirstStepBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.registration_first_step.RegistrationFirstStepPresenter
import medved.studio.pharmix.presentation.registration_first_step.RegistrationFirstStepView
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class RegistrationFirstStepFragment : BaseFragment(R.layout.fragment_registration_first_step),
    RegistrationFirstStepView, BackButtonListener {

    override val contentView by viewBinding(FragmentRegistrationFirstStepBinding::bind)

    @InjectPresenter
    lateinit var presenter: RegistrationFirstStepPresenter

    @ProvidePresenter
    fun providePresenter(): RegistrationFirstStepPresenter {
        return getScope().getInstance(RegistrationFirstStepPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            ivBack.setOnClickListener { onBackPressed() }
            ctiEmail.doAfterTextChange { checkValidField() }
            btnRegistrationNext.setOnClickListener { presenter.nextStep(ctiEmail.text()) }
        }
    }

    private fun checkValidField() {
        contentView.run {
            val email = ctiEmail.text()
            presenter.isValidField(email)
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