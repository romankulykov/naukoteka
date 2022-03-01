package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.media

import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.repositories.dialogs.models.SearchMedia
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentMediaBinding
import uddug.com.naukoteka.di.DI
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.media.MediaPresenter
import uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.media.MediaView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class MediaFragment : BaseFragment(R.layout.fragment_media),
    MediaView, BackButtonListener,
    EndlessRecyclerViewAdapter.RequestToLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @InjectPresenter
    lateinit var presenter: MediaPresenter
    private val localScope by lazy { getScope().openSubScope(DI.SEARCH_IN_CHAT_SCOPE) }

    @ProvidePresenter
    fun providePresenter(): MediaPresenter {
        return localScope.getInstance(MediaPresenter::class.java)
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
        contentView.run {
            rvMedia.adapter = endlessAdapter
            srlList.setOnRefreshListener(this@MediaFragment)
        }
    }

    override fun showRefreshLoading(show: Boolean) {
        contentView.srlList.isRefreshing = show
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

    override fun onRefresh() {
        presenter.obtainData(false)
    }

    override fun onLoadMoreRequested() {
        presenter.obtainData(false, mediaAdapter.getItems()?.lastOrNull()?.messageId)
    }
}