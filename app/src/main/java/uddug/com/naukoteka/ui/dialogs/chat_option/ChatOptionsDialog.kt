package uddug.com.naukoteka.ui.dialogs.chat_option

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.data.ChatOption
import uddug.com.naukoteka.databinding.AdditionalOptionsDialogBinding
import uddug.com.naukoteka.global.base.BaseBottomSheetDialog
import uddug.com.naukoteka.ui.adapters.additional_options.AdditionalOptionsAdapter

class ChatOptionsDialog(
    private val fragmentActivity: FragmentActivity,
    private val chatOptionListener: (ChatOption) -> Unit
) :
    BaseBottomSheetDialog(fragmentActivity) {

    override val layoutRes: Int = R.layout.additional_options_dialog
    override val contentView by lazy { AdditionalOptionsDialogBinding.inflate(layoutInflater) }

    init {
        setContentView(contentView.root)
    }

    private lateinit var options: ArrayList<ChatOption>

    private val chatOptionsAdapter by lazy {
        AdditionalOptionsAdapter {
            dismiss()
            chatOptionListener(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = ArrayList()
        options.run {
            add(ChatOption.SEARCH_BY_CONVERSATION)
            add(ChatOption.INTERVIEW_MATERIALS)
            add(ChatOption.DISABLE_NOTIFICATIONS)
            add(ChatOption.CLEAR_THE_HISTORY)
            add(ChatOption.ADD_PARTICIPANT)
        }
        contentView.run {
            rvAdditionalOptions.adapter = chatOptionsAdapter
            chatOptionsAdapter.setItems(options)
            btnCancel.setOnClickListener { dismiss() }
        }
    }

}