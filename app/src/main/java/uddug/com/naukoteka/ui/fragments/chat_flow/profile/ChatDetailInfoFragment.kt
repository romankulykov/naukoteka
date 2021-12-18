package uddug.com.naukoteka.ui.fragments.chat_flow.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.repositories.participants.ParticipantsEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentChatDetailInfoBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.profile.ChatDetailInfoPresenter
import uddug.com.naukoteka.presentation.chat_flow.profile.ChatDetailInfoView
import uddug.com.naukoteka.ui.fragments.chat_flow.create_group.CreateGroupAdapter
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class ChatDetailInfoFragment :
    BaseFragment(R.layout.fragment_chat_detail_info),
    ChatDetailInfoView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: ChatDetailInfoPresenter

    @ProvidePresenter
    fun providePresenter(): ChatDetailInfoPresenter {
        return getScope().getInstance(ChatDetailInfoPresenter::class.java)
    }

    companion object {
        private const val KEY_TITLE = "ChatDetailInfoFragment.KEY_TITLE"
        fun newInstance(isProfile: Boolean) = ChatDetailInfoFragment().apply {
            arguments = bundleOf().apply { putBoolean(KEY_TITLE, isProfile) }
        }
    }

    private val titles = arrayListOf(
        R.string.media,
        R.string.links,
        R.string.files,
        R.string.audio,
    )

    private val thisIsProfile get() = arguments?.getBoolean(KEY_TITLE) ?: false

    private val createGroupAdapter by lazy { CreateGroupAdapter(presenter::onParticipantRemove) }

    override val contentView by viewBinding(FragmentChatDetailInfoBinding::bind)

    private val profileAdapter by lazy { ChatDetailInfoAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            clBack.setOnClickListener { onBackPressed() }
            viewPager.adapter = profileAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = getString(titles[position])
            }.attach()
            rvParticipants.adapter = createGroupAdapter
            if (!thisIsProfile) {
                tvNameContact.text = getString(R.string.name_group)
                btnGoToProfile.isInvisible = true
                viewDivider1.isInvisible = true
                llSearch.isVisible = false
                llMenu.isVisible = false
                viewBand.isVisible = false
                tvDescriptionGroup.isVisible = true
                tvProjectWork.isVisible = true
                tvCountParticipants.isVisible = true
                rvParticipants.isVisible = true
                llEmail.isVisible = false
                viewDivider2.isVisible = false
                llUserName.isVisible = false
            }
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun showParticipants(participants: List<ParticipantsEntity>) {
        createGroupAdapter.setItems(participants)
        contentView.run {
            tvStatus.text = getString(R.string.count, createGroupAdapter.itemCount)
            tvCountParticipants.text = getString(
                R.string.count_of_participants,
                createGroupAdapter.itemCount
            )
        }

    }
}