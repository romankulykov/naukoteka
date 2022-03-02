package uddug.com.naukoteka.ui.fragments.chat_flow.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope
import toothpick.ktp.KTP
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.DialogType
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentChatDetailInfoBinding
import uddug.com.naukoteka.di.DI
import uddug.com.naukoteka.di.modules.SearchInChatModule
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.profile.ChatDetailInfoPresenter
import uddug.com.naukoteka.presentation.chat_flow.profile.ChatDetailInfoView
import uddug.com.naukoteka.ui.fragments.chat_flow.create_group.CreateGroupAdapter
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.getColorCompat
import uddug.com.naukoteka.utils.ui.TextDrawable
import uddug.com.naukoteka.utils.ui.load
import uddug.com.naukoteka.utils.viewBinding

class ChatDetailInfoFragment : BaseFragment(R.layout.fragment_chat_detail_info),
    ChatDetailInfoView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: ChatDetailInfoPresenter

    @ProvidePresenter
    fun providePresenter(): ChatDetailInfoPresenter {
        return getScope().getInstance(ChatDetailInfoPresenter::class.java)
    }

    companion object {
        private const val CHAT_PREVIEW = "ChatDetailInfoFragment.CHAT_PREVIEW"
        fun newInstance(chatPreview: ChatPreview) = ChatDetailInfoFragment().apply {
            arguments = bundleOf(CHAT_PREVIEW to chatPreview)
        }
    }

    private val titles = arrayListOf(
        R.string.media,
        R.string.links,
        R.string.files,
        R.string.audio,
    )

    private val chatPreview get() = arguments?.getParcelable<ChatPreview>(CHAT_PREVIEW)!!

    private val createGroupAdapter by lazy { CreateGroupAdapter(presenter::onParticipantRemove) }

    override val contentView by viewBinding(FragmentChatDetailInfoBinding::bind)

    private val profileAdapter by lazy { ChatDetailInfoAdapter(this) }

    override fun getScope(): Scope {
        return super.getScope().openSubScope(DI.SEARCH_IN_CHAT_SCOPE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        KTP.closeScope(DI.SEARCH_IN_CHAT_SCOPE)
        getScope().installModules(SearchInChatModule(chatPreview.dialogId))
        contentView.run {
            clBack.setOnClickListener { onBackPressed() }
            viewPager.adapter = profileAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = getString(titles[position])
            }.attach()

            if (chatPreview.dialogImage?.fullPath == null) {
                val previewTextImage = chatPreview.dialogName.split(" ").filter { it.isNotBlank() }
                val drawable = TextDrawable.builder()
                    .buildRound(
                        text = previewTextImage.map { it.first() }.joinToString(""),
                        color = requireContext().getColorCompat(R.color.object_main)
                    )
                ivPhoto.setImageDrawable(drawable)
            } else {
                ivPhoto.load(
                    chatPreview.dialogImage?.fullPath,
                    placeholder = R.drawable.ic_glide_image_error,
                    requestOptions = RequestOptions.centerCropTransform()
                )
            }

            rvParticipants.adapter = createGroupAdapter
            tvNameContact.text = chatPreview.dialogName
            tvStatus.text = if (chatPreview.dialogType == DialogType.GROUP) {
                getString(R.string.count_participants_format, chatPreview.users?.size)
            } else {
                getString(R.string.recent)
            }
            btnGoToProfile.isVisible = chatPreview.dialogType == DialogType.PERSONAL
            llEmail.isVisible = chatPreview.dialogType == DialogType.PERSONAL
            tvEmailValue.text = chatPreview.interlocutor?.nicknameOrFullName

            llUserName.isVisible = chatPreview.dialogType == DialogType.PERSONAL
            tvUserNameValue.text = chatPreview.interlocutor?.nicknameOrFullName
            viewDivider1.isInvisible = chatPreview.dialogType == DialogType.GROUP

            tvDescriptionGroup.isVisible = chatPreview.dialogType == DialogType.GROUP
            tvDescriptionGroupValue.isVisible = chatPreview.dialogType == DialogType.GROUP
            tvDescriptionGroupValue.text = chatPreview.dialogName

            tvCountParticipants.isVisible = chatPreview.dialogType == DialogType.GROUP
            tvCountParticipants.text =
                getString(R.string.count_participants_format, chatPreview.users?.size)
            rvParticipants.isVisible = chatPreview.dialogType == DialogType.GROUP
            rvParticipants.adapter =
                createGroupAdapter.apply { chatPreview.users?.let { setItems(it) } }

            viewDivider2.isVisible = chatPreview.dialogType == DialogType.PERSONAL

            llSearch.setOnClickListener { presenter.navigateToSearch(chatPreview.dialogId) }

        }
    }

    override fun onPause() {
        super.onPause()
        contentView.viewPager.adapter = null
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

}