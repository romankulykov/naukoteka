package medved.studio.pharmix.ui.fragments.chats

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import medved.studio.domain.entities.ChatListEntity
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentChatsBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.chats.ChatsPresenter
import medved.studio.pharmix.presentation.chats.ChatsView
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ChatsFragment : BaseFragment(R.layout.fragment_chats),
    ChatsView, BackButtonListener {

    override val contentView by viewBinding(FragmentChatsBinding::bind)

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

        contentView.rvChatList.adapter = ChatsAdapter().apply { setItems(listOfChatList) }

    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun getChats(chatListEntity: ChatListEntity) {

    }
}