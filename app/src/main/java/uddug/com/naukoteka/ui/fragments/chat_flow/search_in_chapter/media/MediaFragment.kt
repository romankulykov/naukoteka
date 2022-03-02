package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.media

import android.os.Bundle
import android.view.View
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope
import toothpick.Toothpick
import uddug.com.domain.repositories.dialogs.models.SearchMedia
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentMediaBinding
import uddug.com.naukoteka.di.DI
import uddug.com.naukoteka.di.modules.SearchInChatModule
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.media.MediaPresenter
import uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.media.MediaView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class MediaFragment : BaseFragment(R.layout.fragment_media), MediaView, BackButtonListener,
    EndlessRecyclerViewAdapter.RequestToLoadMoreListener {

    @InjectPresenter
    lateinit var presenter: MediaPresenter

    override fun getScope(): Scope {
        return super.getScope().openSubScope(DI.SEARCH_IN_CHAT_SCOPE)
    }

    @ProvidePresenter
    fun providePresenter(): MediaPresenter {
        return getScope().getInstance(MediaPresenter::class.java)
    }

    override val contentView by viewBinding(FragmentMediaBinding::bind)

    private val mediaAdapter = MediaAdapter()

    private val endlessAdapter by lazy {
        EndlessRecyclerViewAdapter(
            context,
            mediaAdapter,
            this,
            R.layout.pagination_progress_bar,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Toothpick.isScopeOpen(DI.SEARCH_IN_CHAT_SCOPE)) {
            try {
                presenter.searchInChat =
                    getScope().getInstance(SearchInChatModule.SearchInChatParams::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        contentView.run {
            rvMedia.adapter = endlessAdapter
        }
        presenter.obtainData()
    }


    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun showMedia(
        media: List<SearchMedia>,
        needClear: Boolean,
        loadMore: Boolean
    ) {
        if (needClear) {
            mediaAdapter.clear()
        }

        mediaAdapter.addItems(media)
        endlessAdapter.onDataReady(loadMore)
    }

    override fun onLoadMoreRequested() {
        presenter.obtainData(false, mediaAdapter.getItems()?.lastOrNull()?.messageId)
    }
}