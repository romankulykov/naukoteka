package medved.studio.pharmix.ui.fragments.chat_list

import android.os.Bundle
import android.view.View
import medved.studio.domain.entities.ChatListEntity
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.FragmentChatListBinding
import medved.studio.pharmix.global.base.BaseFragment
import medved.studio.pharmix.presentation.chat_list.ChatListPresenter
import medved.studio.pharmix.presentation.chat_list.ChatListView
import medved.studio.pharmix.utils.BackButtonListener
import medved.studio.pharmix.utils.viewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ChatListFragment : BaseFragment(R.layout.fragment_chat_list),
    ChatListView, BackButtonListener {

    override val contentView by viewBinding(FragmentChatListBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatListPresenter

    @ProvidePresenter
    fun providePresenter(): ChatListPresenter {
        return getScope().getInstance(ChatListPresenter::class.java)
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

        contentView.rvChatList.adapter = ChatListAdapter().apply { setItems(listOfChatList) }

    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun getChats(chatListEntity: ChatListEntity) {

    }
}