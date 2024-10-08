package uddug.com.naukoteka.ui.fragments.chat_flow.create_group

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.request.RequestOptions
import gun0912.tedbottompicker.TedBottomPicker
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentCreateGroupBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.chat_flow.create_group.CreateGroupPresenter
import uddug.com.naukoteka.presentation.chat_flow.create_group.CreateGroupView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.ui.load
import uddug.com.naukoteka.utils.viewBinding
import uddug.com.naukoteka.utils.withPermissions

class CreateGroupFragment : BaseFragment(R.layout.fragment_create_group),
    CreateGroupView, BackButtonListener {

    companion object {

        private const val KEY_CONTACTS = "CreateGroupFragment.KEY_CONTACTS"

        fun newInstance(selectedContacts: List<UserChatPreview>) = CreateGroupFragment().apply {
            arguments = bundleOf(KEY_CONTACTS to selectedContacts)
        }
    }

    override val contentView by viewBinding(FragmentCreateGroupBinding::bind)

    @InjectPresenter
    lateinit var presenter: CreateGroupPresenter

    @ProvidePresenter
    fun providePresenter(): CreateGroupPresenter {
        return getScope().getInstance(CreateGroupPresenter::class.java)
    }

    private val createGroupAdapter by lazy { CreateGroupAdapter(presenter::onParticipantRemove) }

    private val selectedContacts
        get() = arguments?.getParcelableArrayList<UserChatPreview>(KEY_CONTACTS)

    private var photoUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setUsers(selectedContacts!!)
        contentView.run {
            rvParticipants.adapter = createGroupAdapter
            tvCountSymbols.text = getString(R.string.count_symbols, 0)

            ivPhoto.setOnClickListener {
                context?.withPermissions(CAMERA, WRITE_EXTERNAL_STORAGE) {
                    ivPhoto.pick()
                }
            }

            clBack.setOnClickListener { onBackPressed() }
            tvAddParticipant.setOnClickListener { onBackPressed() }
            btnRemoveSymbols.setOnClickListener { etEnterDescription.setText("") }
            btnCreateGroup.setOnClickListener { presenter.createGroup() }

            etGroupName.doAfterTextChanged {
                tvCountSymbols.text = getString(R.string.count_symbols, etGroupName.text.length)
                presenter.changeGroupName(it.toString())
            }
            etEnterDescription.doAfterTextChanged {
                btnRemoveSymbols.isVisible = !etEnterDescription.text.toString().isEmpty()
            }
        }
    }

    override fun handleEnableCreate(isEnabled : Boolean) {
        contentView.btnCreateGroup.isEnabled  = isEnabled
    }

    private fun ImageView.pick() = TedBottomPicker.with(activity).show {
        load(it, requestOptions = RequestOptions.centerCropTransform())
        photoUri = it
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun showParticipants(participants: List<UserChatPreview>) {
        createGroupAdapter.setItems(participants)
        contentView.tvCountParticipants.text =
            getString(R.string.count_participants, createGroupAdapter.itemCount)
    }
}