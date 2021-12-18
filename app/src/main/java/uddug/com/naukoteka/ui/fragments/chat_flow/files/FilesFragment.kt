package uddug.com.naukoteka.ui.fragments.chat_flow.files

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.entities.FilesEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentFilesBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.files.FilesPresenter
import uddug.com.naukoteka.presentation.chat_flow.files.FilesView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class FilesFragment : BaseFragment(R.layout.fragment_files),
    FilesView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: FilesPresenter

    @ProvidePresenter
    fun providePresenter(): FilesPresenter {
        return getScope().getInstance(FilesPresenter::class.java)
    }

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

    override fun showFiles(files: List<FilesEntity>) {
        filesAdapter.setItems(files)
    }
}