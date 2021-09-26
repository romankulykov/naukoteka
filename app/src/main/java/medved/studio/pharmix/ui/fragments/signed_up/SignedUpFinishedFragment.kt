package medved.studio.pharmix.ui.fragments.signed_up

import android.os.Bundle
import android.view.View
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentSignUpFinishedBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.signed_up.SignedUpFinishedPresenter
import medved.studio.pharmix.presentation.signed_up.SignedUpView
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class SignedUpFinishedFragment : BaseFragment(R.layout.fragment_sign_up_finished),
    SignedUpView {

    override val contentView by viewBinding(FragmentSignUpFinishedBinding::bind)

    @InjectPresenter
    lateinit var presenter: SignedUpFinishedPresenter

    @ProvidePresenter
    fun providePresenter(): SignedUpFinishedPresenter {
        return getScope().getInstance(SignedUpFinishedPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            tvSkip.setOnClickListener { presenter.skip() }
            btnFillInProfile.setOnClickListener { presenter.fillProfile() }
        }
    }

}