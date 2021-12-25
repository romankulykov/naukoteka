package uddug.com.naukoteka.ui.fragments.chat_flow.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.repositories.dialogs.models.ChatsPreview
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatLongPressMenu
import uddug.com.naukoteka.data.ChatSwipeTitleOption
import uddug.com.naukoteka.data.ChatTitleActionDialog
import uddug.com.naukoteka.databinding.FragmentChatsBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.chats.ChatsPresenter
import uddug.com.naukoteka.presentation.chat_flow.chats.ChatsView
import uddug.com.naukoteka.ui.adapters.long_press_menu.LongPressMenuAdapter
import uddug.com.naukoteka.ui.dialogs.chat_option.ChatOptionsDialogType
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class ChatsFragment : BaseFragment(R.layout.fragment_chats), ChatsView, BackButtonListener {

    companion object {
        private const val KEY_TITLE = "ChatsFragment.KEY_TITLE"

        fun newInstance(titlesList: ArrayList<Int>?) = ChatsFragment().apply {
            arguments = bundleOf().apply { putIntegerArrayList(KEY_TITLE, titlesList) }
        }
    }

    override val contentView by viewBinding(FragmentChatsBinding::bind)


    @InjectPresenter
    lateinit var presenter: ChatsPresenter

    @ProvidePresenter
    fun providePresenter(): ChatsPresenter {
        return getScope().getInstance(ChatsPresenter::class.java)
    }

    private val chatsAdapter by lazy {
        ChatsAdapter(
            presenter::onChatClick,
            ::showPopupLongPressMenu,
            ::showSwipeClick
        )
    }

    private lateinit var longOptions: ArrayList<ChatLongPressMenu>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            rvChatList.adapter = chatsAdapter
        }

    }

    override fun showChats(chatsPreview: ChatsPreview) {
        chatsAdapter.setItems(chatsPreview.dialogs)
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

                }
            }
        }.show()
    }

    private fun showPopupLongPressMenu(v: View) {
        contentView.run {
            longOptions = ArrayList()
            longOptions.run {
                add(ChatLongPressMenu.ANCHOR_CHAT)
                add(ChatLongPressMenu.HIDE_CHAT)
                add(ChatLongPressMenu.DISABLE_NOTIFICATIONS)
                add(ChatLongPressMenu.CLEAR_THE_HISTORY)
                add(ChatLongPressMenu.BLOCK)
            }
            var child =
                LayoutInflater.from(requireContext()).inflate(R.layout.popup_long_press_menu, null)
            val popupWindow = PopupWindow(requireContext())
            val longPressMenuAdapter = LongPressMenuAdapter { popupWindow.dismiss() }
            with(popupWindow) {
                contentView = child
                setBackgroundDrawable(null)
                elevation = 12F
                isFocusable = true
                isOutsideTouchable = true
                showAsDropDown(v, 0, 0)
                with(child) {
                    findViewById<RecyclerView>(R.id.rv_popup_long_press_menu).adapter =
                        longPressMenuAdapter.apply { setItems(longOptions) }
                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

}