package uddug.com.naukoteka.ui.dialogs.chat_option

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.MediaStore.Files.FileColumns.*
import androidx.fragment.app.FragmentActivity
import toothpick.ktp.delegate.inject
import uddug.com.domain.entities.AndroidFileEntity
import uddug.com.domain.interactors.internal.AndroidFilesInteractor
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatAttachmentOption
import uddug.com.naukoteka.databinding.AttachmentFileDialogBinding
import uddug.com.naukoteka.global.base.BaseBottomSheetDialog
import uddug.com.naukoteka.ui.adapters.attachment_options.AttachmentOptionsAdapter
import uddug.com.naukoteka.ui.adapters.attachment_options.AttachmentPhotoAdapter

class AttachmentOptionsDialog(
    private val fragmentActivity: FragmentActivity,
    private val chatAttachmentOptionListener: (ChatAttachmentOption) -> Unit,
    private val chatAttachmentPhotoListener: (AndroidFileEntity) -> Unit
) :
    BaseBottomSheetDialog(fragmentActivity) {

    override val layoutRes: Int = R.layout.attachment_file_dialog
    override val contentView by lazy { AttachmentFileDialogBinding.inflate(layoutInflater) }
    private val fileSource: AndroidFilesInteractor by inject()

    init {
        getScope()
            .inject(this)
        setContentView(contentView.root)
    }

    private lateinit var options: ArrayList<ChatAttachmentOption>

    private val chatAttachmentOptionsAdapter by lazy {
        AttachmentOptionsAdapter {
            dismiss()
            chatAttachmentOptionListener(it)
        }
    }

    val photoAttachmentAdapter by lazy {
        AttachmentPhotoAdapter {
            dismiss()
            chatAttachmentPhotoListener(it)
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = ArrayList()
        options.run {
            add(ChatAttachmentOption.PHOTO_OR_VIDEO)
            add(ChatAttachmentOption.FILE)
            add(ChatAttachmentOption.CONTACT)
            add(ChatAttachmentOption.INTERROGATION)
        }
        contentView.run {
            rvAttachmentOptions.adapter = chatAttachmentOptionsAdapter
            chatAttachmentOptionsAdapter.setItems(options)
            btnCancel.setOnClickListener { dismiss() }
            rvPhotos.adapter = photoAttachmentAdapter
        }
        fileSource.allFile("$MEDIA_TYPE=$MEDIA_TYPE_IMAGE OR $MEDIA_TYPE=$MEDIA_TYPE_VIDEO")
            .subscribe(this::showPhotos) { }
    }

    fun showPhotos(files: List<AndroidFileEntity>) {
        photoAttachmentAdapter.setItems(files)
    }

}