package medved.studio.pharmix.ui.fragments.tutorial

import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
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

class TutorialFragment : BaseFragment(R.layout.fragment_tutorial_search), TutorialView,
    BackButtonListener {

    override val contentView by viewBinding(FragmentTutorialSearchBinding::bind)

    @InjectPresenter
    lateinit var presenter: TutorialPresenter

    @ProvidePresenter
    fun providePresenter(): TutorialPresenter {
        return getScope().getInstance(TutorialPresenter::class.java)
    }

    override fun showTutorialInformation(resources: List<TutorialEntity>) {
        contentView.run {
            tvSkip.setOnClickListener {
                presenter.finishTutorial()
            }
            viewPager.adapter = TutorialAdapter().apply { setItems(resources) }
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tvSkip.isVisible = position != 2
                    if (position == 2) {
                        btnTutorialSearchNext.text = getString(R.string.ready)
                        btnTutorialSearchNext.setOnClickListener {
                            presenter.finishTutorial()
                        }
                    } else {
                        btnTutorialSearchNext.text = getString(R.string.next)
                        btnTutorialSearchNext.setOnClickListener {
                            val getItem = viewPager.currentItem
                            viewPager.setCurrentItem(getItem + 1, true)
                        }
                    }
                }
            })
            springDotsIndicator.setViewPager2(viewPager)
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return false
    }
}