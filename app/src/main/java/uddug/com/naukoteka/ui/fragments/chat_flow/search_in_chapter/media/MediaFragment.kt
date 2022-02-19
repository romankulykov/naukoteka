package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.media

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.entities.MediaEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentMediaBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.media.MediaPresenter
import uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.media.MediaView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class MediaFragment : BaseFragment(R.layout.fragment_media),
    MediaView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: MediaPresenter

    @ProvidePresenter
    fun providePresenter(): MediaPresenter {
        return getScope().getInstance(MediaPresenter::class.java)
    }

    override val contentView by viewBinding(FragmentMediaBinding::bind)

    private val mediaAdapter = MediaAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            rvMedia.adapter = mediaAdapter
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun showMedia(media: List<MediaEntity>) {
        mediaAdapter.setItems(media)
    }
}