package medved.studio.pharmix.ui.fragments.registration_third_step

import android.os.Bundle
import android.view.View
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentRegistrationThirdStepBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.registration_third_step.RegistrationThirdStepPresenter
import medved.studio.pharmix.presentation.registration_third_step.RegistrationThirdStepView
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class RegistrationThirdStepFragment : BaseFragment(R.layout.fragment_registration_third_step),
    RegistrationThirdStepView {

    @InjectPresenter
    lateinit var presenter: RegistrationThirdStepPresenter

    @ProvidePresenter
    fun providePresenter(): RegistrationThirdStepPresenter {
        return getScope().getInstance(RegistrationThirdStepPresenter::class.java)
    }

    override val contentView by viewBinding(FragmentRegistrationThirdStepBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showSecondsToResend(seconds: Int) {
        if (seconds > 0) {
            val minutes = seconds / 60
            val seconds = seconds - (minutes * 60)
            val secondsLeft = String.format("%02d:%02d", minutes, seconds)
            contentView.run {
                tvSendLetter.isVisible = true
                tvSendLetter.text =
                    getString(R.string.registration_send_letter, secondsLeft).parseAsHtml()
                tvSendLetter.setOnClickListener {  }
            }
        } else if (seconds == 0) {
            contentView.run {
                tvSendLetter.text =
                    getString(R.string.registration_send_letter, "").parseAsHtml()
                tvSendLetter.setOnClickListener { presenter.resendCode() }
            }
        }
    }
}