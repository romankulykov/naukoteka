package uddug.com.naukoteka.ui.fragments.links

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.entities.LinksEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentsLinksBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.links.LinksPresenter
import uddug.com.naukoteka.presentation.links.LinksView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class LinksFragment : BaseFragment(R.layout.fragments_links),
    LinksView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: LinksPresenter

    @ProvidePresenter
    fun providePresenter(): LinksPresenter {
        return getScope().getInstance(LinksPresenter::class.java)
    }

    override val contentView by viewBinding(FragmentsLinksBinding::bind)

    private val linksAdapter = LinksAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            rvLinks.adapter = linksAdapter
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun showLinks(links: List<LinksEntity>) {
        linksAdapter.setItems(links)
    }
}