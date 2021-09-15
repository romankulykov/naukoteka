package medved.studio.pharmix.ui.fragments.tutorial

import medved.studio.domain.entities.TutorialEntity
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentTutorialSearchBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.tutorial.TutorialPresenter
import medved.studio.pharmix.presentation.tutorial.TutorialView
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class TutorialFragment : BaseFragment(R.layout.fragment_tutorial_search), TutorialView, BackButtonListener {

    override val contentView by viewBinding(FragmentTutorialSearchBinding::bind)

    @InjectPresenter
    lateinit var presenter: TutorialPresenter

    @ProvidePresenter
    fun providePresenter(): TutorialPresenter {
        return getScope().getInstance(TutorialPresenter::class.java)
    }

    override fun showTutorialInformation(resources: List<TutorialEntity>) {
        contentView.run {
            viewPager.adapter = TutorialAdapter().apply { setItems(resources) }
            springDotsIndicator.setViewPager2(viewPager)
            btnFindAMedicineInPharmacies.setOnClickListener { presenter.finishTutorial() }
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return false
    }
}