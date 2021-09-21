package medved.studio.pharmix.ui.fragments.short_info_profile

import android.os.Bundle
import android.view.View
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentShortInfoProfileBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.short_info_profile.ShortInfoProfilePresenter
import medved.studio.pharmix.presentation.short_info_profile.ShortInfoProfileView
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ShortInfoProfileFragment : BaseFragment(R.layout.fragment_short_info_profile),
    ShortInfoProfileView {

    override val contentView by viewBinding(FragmentShortInfoProfileBinding::bind)

    @InjectPresenter
    lateinit var presenter: ShortInfoProfilePresenter

    @ProvidePresenter
    fun providePresenter(): ShortInfoProfilePresenter {
        return getScope().getInstance(ShortInfoProfilePresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            ctiLink.doAfterTextChange { checkValidFields() }
            ctiName.doAfterTextChange { checkValidFields() }
            ctiPatronymic.doAfterTextChange { checkValidFields() }
            ctiSurname.doAfterTextChange { checkValidFields() }
            btnCompleteRegistration.setOnClickListener { presenter.nextStep() }
        }
    }

    private fun checkValidFields() {
        contentView.run {
            val surname = ctiSurname.text()
            val name = ctiName.text()
            val patronymic = ctiPatronymic.text()
            val link = ctiLink.text()
            presenter.isValidFields(surname, name, patronymic, link)
        }
    }

    override fun showButtonState(isEnabled: Boolean) {
        contentView.btnCompleteRegistration.isEnabled = isEnabled
    }
}