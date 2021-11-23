package uddug.com.naukoteka.ui.fragments.create_group

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import gun0912.tedbottompicker.TedBottomPicker
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.repositories.participants.ParticipantsEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentCreateGroupBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.create_group.CreateGroupPresenter
import uddug.com.naukoteka.presentation.create_group.CreateGroupView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.textColor
import uddug.com.naukoteka.utils.ui.load
import uddug.com.naukoteka.utils.viewBinding
import uddug.com.naukoteka.utils.withPermissions

class CreateGroupFragment : BaseFragment(R.layout.fragment_create_group),
    CreateGroupView, BackButtonListener {

    override val contentView by viewBinding(FragmentCreateGroupBinding::bind)

    @InjectPresenter
    lateinit var presenter: CreateGroupPresenter

    @ProvidePresenter
    fun providePresenter(): CreateGroupPresenter {
        return getScope().getInstance(CreateGroupPresenter::class.java)
    }

    private val createGroupAdapter by lazy { CreateGroupAdapter(presenter::onParticipantRemove) }

    private var photoUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoUri?.let { activity?.contentResolver?.openInputStream(it) }
        contentView.run {
            clBack.setOnClickListener {
                onBackPressed()
            }
            btnRemoveSymbols.setOnClickListener {
                etEnterDescription.setText("")
            }
            rvParticipants.adapter = createGroupAdapter
            tvCountSymbols.text =
                getString(
                    R.string.count_symbols,
                    0
                )
            btnCreateGroup.isEnabled = false
            ivPhoto.setOnClickListener {
                context?.withPermissions(CAMERA, WRITE_EXTERNAL_STORAGE) {
                    ivPhoto.pick()
                }
            }
            etGroupName.doAfterTextChanged {
                btnCreateGroup.isEnabled = it?.isNotEmpty() == true
                if (btnCreateGroup.isEnabled) {
                    btnCreateGroup.setTextColor(Color.WHITE)
                } else {
                    context?.resources?.getColor(R.color.object_inactive)?.let { it1 ->
                        btnCreateGroup.setTextColor(
                            it1
                        )
                    }
                }
                tvCountSymbols.text =
                    getString(
                        R.string.count_symbols,
                        etGroupName.text.length
                    )
            }
            etEnterDescription.doAfterTextChanged {
                btnRemoveSymbols.isVisible = !etEnterDescription.text.toString().isEmpty()
            }
        }
    }

    private fun ImageView.pick() = TedBottomPicker.with(activity).show {
        load(it)
        photoUri = it
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun showParticipants(participants: List<ParticipantsEntity>) {
        createGroupAdapter.setItems(participants)
        contentView.tvCountParticipants.text =
            getString(R.string.count_participants, createGroupAdapter.itemCount)
    }
}