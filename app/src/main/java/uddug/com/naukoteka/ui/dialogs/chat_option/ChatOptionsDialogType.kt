package uddug.com.naukoteka.ui.dialogs.chat_option

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.BottomSheetDialog
import uddug.com.naukoteka.data.BottomSheetDialogOption
import uddug.com.naukoteka.databinding.AdditionalOptionsDialogBinding
import uddug.com.naukoteka.global.base.BaseBottomSheetDialog
import uddug.com.naukoteka.ui.adapters.additional_options.AdditionalOptionsAdapter

class ChatOptionsDialogType(
    fragmentActivity: FragmentActivity,
    private val bottomSheetDialogType: BottomSheetDialog,
    private val chatOptionListener: (BottomSheetDialogOption) -> Unit
) :
    BaseBottomSheetDialog(fragmentActivity) {

    override val layoutRes: Int = R.layout.additional_options_dialog
    override val contentView by lazy { AdditionalOptionsDialogBinding.inflate(layoutInflater) }

    init {
        setContentView(contentView.root)
    }

    private val chatOptionsAdapter by lazy {
        AdditionalOptionsAdapter {
            dismiss()
            chatOptionListener(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO check is dialog personal or not and show appropriate

        contentView.run {
            rvAdditionalOptions.adapter = chatOptionsAdapter
            chatOptionsAdapter.setItems(bottomSheetDialogType.create())
            btnCancel.setOnClickListener { dismiss() }
        }
    }

}