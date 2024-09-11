package uddug.com.naukoteka.ui.fragments.chat_flow.chats

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.ChatsPreview
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatSwipeTitleOption
import uddug.com.naukoteka.data.ChatTitleActionDialog
import uddug.com.naukoteka.data.DialogLongPressMenu.*
import uddug.com.naukoteka.databinding.FragmentChatsBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.chats.ChatsPresenter
import uddug.com.naukoteka.presentation.chat_flow.chats.ChatsView
import uddug.com.naukoteka.ui.dialogs.chat_option.ChatOptionsDialogType
import uddug.com.naukoteka.ui.fragments.chat_flow.chats_list_detail.ChatsListDetailFragment
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.ui.getScreenHeight
import uddug.com.naukoteka.utils.ui.px
import uddug.com.naukoteka.utils.ui.showPopupLongPress
import uddug.com.naukoteka.utils.viewBinding


class ChatsFragment : BaseFragment(R.layout.fragment_chats), ChatsView, BackButtonListener,
    EndlessRecyclerViewAdapter.RequestToLoadMoreListener,
    SwipeRefreshLayout.OnRefreshListener {

    override val contentView by viewBinding(FragmentChatsBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatsPresenter

    @ProvidePresenter
    fun providePresenter(): ChatsPresenter {
        return getScope().getInstance(ChatsPresenter::class.java)
    }

    private val endlessAdapter by lazy {
        EndlessRecyclerViewAdapter(
            context,
            chatsAdapter,
            this,
            R.layout.pagination_progress_bar,
            false
        )
    }

    private val chatsAdapter by lazy {
        ChatsAdapter(
            presenter::onChatClick,
            ::showPopupLongPressMenu,
            ::showSwipeClick
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            rvChatList.adapter = endlessAdapter
            tvCreateChat.setOnClickListener { presenter.showCreateChat() }
            srlList.setOnRefreshListener(this@ChatsFragment)
        }

    }

    override fun showChats(
        chatsPreview: ChatsPreview,
        needClear: Boolean,
        loadMore: Boolean,
        onlyUpdateExist: Boolean
    ) {
        contentView.run {
            srlList.isVisible = chatsPreview.dialogs.isNotEmpty()
            llCreateNewChat.isVisible = chatsPreview.dialogs.isEmpty()
        }
        if (onlyUpdateExist) {
            chatsAdapter.updateItems(chatsPreview.dialogs)
        } else {
            if (needClear) {
                chatsAdapter.clear()
            }
            chatsAdapter.addItems(chatsPreview.dialogs)
            endlessAdapter.onDataReady(loadMore)
        }
    }

    override fun deleteChat(dialog: ChatPreview) {
        chatsAdapter.removeItem(dialog)
    }

    override fun updateDialog(dialog: ChatPreview) {
        chatsAdapter.updateItem(dialog)
    }

    override fun updateOnlineStatus(onlineUsers: List<String>) {
        chatsAdapter.updateStatuses(onlineUsers)
    }

    private fun showSwipeClick(chatSwipeParams: ChatSwipeParams) {
        when {
            chatSwipeParams.chatSwipeOption == ChatSwipeTitleOption.READ -> {
                presenter.readMessages(chatSwipeParams.chatListEntity.dialogId)
            }
            chatSwipeParams.chatSwipeOption == ChatSwipeTitleOption.PIN_TOGGLE && chatSwipeParams.chatListEntity.isPinned -> {
                presenter.togglePin(chatSwipeParams.chatListEntity)
            }
            else -> {
                val title = getString(
                    when (chatSwipeParams.chatSwipeOption) {
                        ChatSwipeTitleOption.BLOCK -> R.string.block_chat_with
                        ChatSwipeTitleOption.CLEAR -> R.string.clear_chat_with
                        ChatSwipeTitleOption.PIN_TOGGLE -> R.string.pin_chat_with
                        else -> R.string.clear_chat_with
                    },
                    chatSwipeParams.chatListEntity.dialogName
                )
                ChatOptionsDialogType(
                    requireActivity(),
                    ChatTitleActionDialog(
                        title,
                        chatSwipeParams.chatSwipeOption,
                        chatSwipeParams.chatListEntity
                    )
                ) {
                    (it as? ChatSwipeTitleOption)?.let {
                        when (it) {
                            ChatSwipeTitleOption.CLEAR -> {
                                presenter.deleteDialog(chatSwipeParams.chatListEntity)
                            }
                            ChatSwipeTitleOption.PIN_TOGGLE -> {
                                presenter.togglePin(chatSwipeParams.chatListEntity)
                            }
                        }
                    }
                }.show()
            }
        }
    }

    private fun showPopupLongPressMenu(chatPreview: ChatPreview, anchor: View) {
        val items = presenter.getDialogLongPressMenu(chatPreview)

        val (realTapOnScreenCoordinate, heightPossibleToDisplayPopup) = calculatePopupAxis(anchor)

        requireActivity().showPopupLongPress(items,
            anchor,
            realTapOnScreenCoordinate,
            heightPossibleToDisplayPopup,
            itemClick = { menuItem ->
                when (menuItem) {
                    BLOCK -> showSwipeClick(ChatSwipeParams(chatPreview, ChatSwipeTitleOption.BLOCK))
                    CLEAR_THE_HISTORY -> showSwipeClick(ChatSwipeParams(chatPreview, ChatSwipeTitleOption.CLEAR))
                    PIN_CHAT, UNPIN_CHAT -> presenter.togglePin(chatPreview)
                    ENABLE_NOTIFICATIONS, DISABLE_NOTIFICATIONS -> presenter.toggleNotifications(chatPreview)
                }
            })
    }

    private fun calculatePopupAxis(anchor: View): Pair<Float, Int> {
        val screenHeight = requireActivity().getScreenHeight()
        val coordinateY = anchor.y
        val topBarHeight =
            (parentFragment as? ChatsListDetailFragment)?.getAppBarLayoutHeight() ?: 0
        val bottomBarHeight = 87.px

        val heightPossibleToDisplayPopup = screenHeight - topBarHeight - bottomBarHeight
        val heightListItem = chatsAdapter.getHeightItem()
        val realTapOnScreenCoordinate = coordinateY + topBarHeight + heightListItem

        return realTapOnScreenCoordinate to heightPossibleToDisplayPopup
    }

    override fun showRefreshLoading(show: Boolean) {
        contentView.srlList.isRefreshing = show
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun onRefresh() {
        presenter.getDialogs(true)
    }

    override fun onLoadMoreRequested() {
        presenter.getDialogs(false, chatsAdapter.getItems()?.lastOrNull()?.messageId)
    }

}