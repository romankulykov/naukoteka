package uddug.com.naukoteka.ui.fragments.chat_flow.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.entities.ChatListEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatLongPressMenu
import uddug.com.naukoteka.databinding.FragmentChatsBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.chats.ChatsPresenter
import uddug.com.naukoteka.presentation.chat_flow.chats.ChatsView
import uddug.com.naukoteka.ui.adapters.long_press_menu.LongPressMenuAdapter
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class ChatsFragment : BaseFragment(R.layout.fragment_chats), ChatsView, BackButtonListener {

    override val contentView by viewBinding(FragmentChatsBinding::bind)

    private val longPressMenuAdapter = LongPressMenuAdapter()

    private lateinit var longOptions: ArrayList<ChatLongPressMenu>

    @InjectPresenter
    lateinit var presenter: ChatsPresenter

    @ProvidePresenter
    fun providePresenter(): ChatsPresenter {
        return getScope().getInstance(ChatsPresenter::class.java)
    }

    companion object {
        private const val KEY_TITLE = "ChatsFragment.KEY_TITLE"

        fun newInstance(titlesList: ArrayList<Int>?) = ChatsFragment().apply {
            arguments = bundleOf().apply { putIntegerArrayList(KEY_TITLE, titlesList) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listOfChatList = listOf(
            ChatListEntity(R.string.contact_name_1, R.string.text_message_1),
            ChatListEntity(R.string.contact_name_2, R.string.text_message_2),
            ChatListEntity(R.string.contact_name_3, R.string.text_message_3),
            ChatListEntity(R.string.contact_name_4, R.string.text_message_4),
            ChatListEntity(R.string.contact_name_5, R.string.text_message_5),
            ChatListEntity(R.string.contact_name_6, R.string.text_message_6),
            ChatListEntity(R.string.contact_name_7, R.string.text_message_7),
            ChatListEntity(R.string.contact_name_8, R.string.text_message_8),
            ChatListEntity(R.string.contact_name_9, R.string.text_message_9),
            ChatListEntity(R.string.contact_name_10, R.string.text_message_10),
        )
        contentView.rvChatList.adapter =
            ChatsAdapter(presenter::onChatClick, ::showPopupLongPressMenu).apply {
                setItems(
                    listOfChatList
                )
            }

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

    override fun getChats(chatListEntity: ChatListEntity) {

    }
}