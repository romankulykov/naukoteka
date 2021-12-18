package uddug.com.naukoteka.ui.dialogs.chat_option

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import uddug.com.domain.entities.AttachmentPhotoEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatAttachmentOption
import uddug.com.naukoteka.databinding.AttachmentFileDialogBinding
import uddug.com.naukoteka.global.base.BaseBottomSheetDialog
import uddug.com.naukoteka.ui.adapters.attachment_options.AttachmentOptionsAdapter
import uddug.com.naukoteka.ui.adapters.attachment_options.AttachmentPhotoAdapter

class AttachmentOptionsDialog(
    private val fragmentActivity: FragmentActivity,
    private val chatAttachmentOptionListener: (ChatAttachmentOption) -> Unit,
    private val chatAttachmentPhotoListener: (AttachmentPhotoEntity) -> Unit
) :
    BaseBottomSheetDialog(fragmentActivity) {

    override val layoutRes: Int = R.layout.attachment_file_dialog
    override val contentView by lazy { AttachmentFileDialogBinding.inflate(layoutInflater) }

    init {
        setContentView(contentView.root)
    }

    private lateinit var options: ArrayList<ChatAttachmentOption>
    private lateinit var photos: ArrayList<AttachmentPhotoEntity>

    private val chatAttachmentOptionsAdapter by lazy {
        AttachmentOptionsAdapter {
            dismiss()
            chatAttachmentOptionListener(it)
        }
    }

    private val photoAttachmentAdapter by lazy {
        AttachmentPhotoAdapter {
            dismiss()
            chatAttachmentPhotoListener(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = ArrayList()
        options.run {
            add(ChatAttachmentOption.PHOTO_OR_VIDEO)
            add(ChatAttachmentOption.FILE)
            add(ChatAttachmentOption.CONTACT)
            add(ChatAttachmentOption.INTERROGATION)
        }
        photos = ArrayList()
        photos.run {
            add(AttachmentPhotoEntity(R.drawable.ic_logo))
            add(AttachmentPhotoEntity(R.drawable.ic_logo))
            add(AttachmentPhotoEntity(R.drawable.ic_logo))
            add(AttachmentPhotoEntity(R.drawable.ic_logo))
            add(AttachmentPhotoEntity(R.drawable.ic_logo))
            add(AttachmentPhotoEntity(R.drawable.ic_logo))
            add(AttachmentPhotoEntity(R.drawable.ic_logo))
        }
        contentView.run {
            rvAttachmentOptions.adapter = chatAttachmentOptionsAdapter
            chatAttachmentOptionsAdapter.setItems(options)
            btnCancel.setOnClickListener { dismiss() }
            rvPhotos.adapter = photoAttachmentAdapter
            photoAttachmentAdapter.setItems(photos)
        }
    }
}