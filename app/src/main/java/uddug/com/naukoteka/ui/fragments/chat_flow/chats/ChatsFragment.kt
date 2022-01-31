package uddug.com.naukoteka.ui.fragments.chat_flow.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rockerhieu.rvadapter.endless.EndlessRecyclerViewAdapter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.ChatsPreview
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatSwipeTitleOption
import uddug.com.naukoteka.data.ChatTitleActionDialog
import uddug.com.naukoteka.data.DialogLongPressMenu
import uddug.com.naukoteka.databinding.FragmentChatsBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.chats.ChatsPresenter
import uddug.com.naukoteka.presentation.chat_flow.chats.ChatsView
import uddug.com.naukoteka.ui.adapters.long_press_menu.LongPressMenuAdapter
import uddug.com.naukoteka.ui.dialogs.chat_option.ChatOptionsDialogType
import uddug.com.naukoteka.utils.BackButtonListener
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

    override fun showChats(chatsPreview: ChatsPreview, needClear: Boolean, loadMore: Boolean) {
        contentView.run {
            srlList.isVisible = chatsPreview.dialogs.isNotEmpty()
            llCreateNewChat.isVisible = chatsPreview.dialogs.isEmpty()
        }
        if (needClear) {
            chatsAdapter.clear()
        }
        chatsAdapter.addItems(chatsPreview.dialogs)
        endlessAdapter.onDataReady(loadMore)
    }

    override fun deleteChat(dialog: ChatPreview) {
        chatsAdapter.removeItem(dialog)
    }

    private fun showSwipeClick(chatSwipeParams: ChatSwipeParams) {
        val title = getString(
            when (chatSwipeParams.chatSwipeOption) {
                ChatSwipeTitleOption.BLOCK -> R.string.block_chat_with
                ChatSwipeTitleOption.CLEAR -> R.string.clear_chat_with
                else -> R.string.clear_chat_with
            },
            chatSwipeParams.chatListEntity.dialogName
        )
        ChatOptionsDialogType(
            requireActivity(),
            ChatTitleActionDialog(title, chatSwipeParams.chatSwipeOption)
        ) {
            (it as? ChatSwipeTitleOption)?.let {
                when (it) {
                    ChatSwipeTitleOption.CLEAR -> {
                        presenter.deleteDialog(chatSwipeParams.chatListEntity)
                    }
                }
            }
        }.show()
    }

    private fun showPopupLongPressMenu(chatPreview: ChatPreview, v: View) {
        contentView.run {
            var child =
                LayoutInflater.from(requireContext()).inflate(R.layout.popup_long_press_menu, null)
            val popupWindow = PopupWindow(requireContext())
            val longPressMenuAdapter = LongPressMenuAdapter {
                when (it) {
                    DialogLongPressMenu.BLOCK -> {
                        showSwipeClick(ChatSwipeParams(chatPreview, ChatSwipeTitleOption.BLOCK))
                    }
                    DialogLongPressMenu.CLEAR_THE_HISTORY -> {
                        showSwipeClick(ChatSwipeParams(chatPreview, ChatSwipeTitleOption.CLEAR))
                    }
                }
                popupWindow.dismiss()
            }
            with(popupWindow) {
                contentView = child
                setBackgroundDrawable(null)
                elevation = 12F
                isFocusable = true
                isOutsideTouchable = true
                showAsDropDown(v, 0, 0)
                with(child) {
                    findViewById<RecyclerView>(R.id.rv_popup_long_press_menu).adapter =
                        longPressMenuAdapter.apply {
                            setItems(
                                DialogLongPressMenu.values().toList()
                            )
                        }
                }
            }
        }
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
        presenter.getDialogs(false, chatsAdapter.getItems()?.last()?.lastMessage?.id)
    }

}