package medved.studio.pharmix.ui.fragments.signed_up

import android.os.Bundle
import android.view.View
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentSignedUpBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.signed_up.SignedUpPresenter
import medved.studio.pharmix.presentation.signed_up.SignedUpView
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class SignedUpFragment : BaseFragment(R.layout.fragment_signed_up),
    SignedUpView {

    override val contentView by viewBinding(FragmentSignedUpBinding::bind)

    @InjectPresenter
    lateinit var presenter: SignedUpPresenter

    @ProvidePresenter
    fun providePresenter(): SignedUpPresenter {
        return getScope().getInstance(SignedUpPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}