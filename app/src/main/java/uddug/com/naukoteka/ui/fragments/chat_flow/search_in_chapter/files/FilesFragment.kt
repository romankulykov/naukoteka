package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.files

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.entities.FilesEntity
import uddug.com.domain.repositories.dialogs.models.SearchMedia
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentFilesBinding
import uddug.com.naukoteka.di.DI
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.files.FilesPresenter
import uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.files.FilesView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class FilesFragment : BaseFragment(R.layout.fragment_files),
    FilesView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: FilesPresenter

    @ProvidePresenter
    fun providePresenter(): FilesPresenter {
        return localScope.getInstance(FilesPresenter::class.java)
    }

    private val localScope by lazy { getScope().openSubScope(DI.SEARCH_IN_CHAT_SCOPE) }
    override val contentView by viewBinding(FragmentFilesBinding::bind)

    private val filesAdapter = FilesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            rvLinks.adapter = filesAdapter
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun showFiles(files: List<SearchMedia>) {
        filesAdapter.setItems(files)
    }
}