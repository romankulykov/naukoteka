package uddug.com.naukoteka.ui.fragments.profile

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentProfileBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.profile.ProfilePresenter
import uddug.com.naukoteka.presentation.profile.ProfileView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class ProfileFragment : BaseFragment(R.layout.fragment_profile),
    ProfileView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter {
        return getScope().getInstance(ProfilePresenter::class.java)
    }

    private val titles = arrayListOf(
        R.string.media,
        R.string.links,
        R.string.files,
        R.string.audio,
    )

    override val contentView by viewBinding(FragmentProfileBinding::bind)

    private val profileAdapter by lazy { ProfileAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            clBack.setOnClickListener { onBackPressed() }
            viewPager.adapter = profileAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = getString(titles[position])
            }.attach()
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }
}