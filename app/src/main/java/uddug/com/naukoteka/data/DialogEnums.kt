package uddug.com.naukoteka.data

import android.view.Gravity
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.naukoteka.R

sealed class BottomSheetDialog {

    fun create(): ArrayList<BottomSheetDialogEntity> {
        return when (this) {
            is ChatOptionsDialog -> ArrayList<BottomSheetDialogEntity>().apply {
                addAll(ChatOption.values().map { ChatOptionEntity(it) })
            }
            is ChatTitleActionDialog -> {
                ArrayList<BottomSheetDialogEntity>().apply {
                    add(
                        TitleDialogEntity(
                            text = title,
                            clickable = false,
                            titleActionOption = option
                        )
                    )
                    add(
                        TitleDialogEntity(
                            textId = when (option) {
                                ChatSwipeTitleOption.BLOCK -> R.string.swipe_block
                                ChatSwipeTitleOption.CLEAR -> R.string.clear_chat
                                ChatSwipeTitleOption.PIN_TOGGLE -> if (chatPreview.isPinned) R.string.unpin_chat else R.string.pin_chat
                            },
                            clickable = true,
                            titleActionOption = option
                        )
                    )
                }
            }
        }
    }

}

object ChatOptionsDialog : BottomSheetDialog()
data class ChatTitleActionDialog(
    val title: String,
    val option: ChatSwipeTitleOption,
    val chatPreview: ChatPreview
) :
    BottomSheetDialog()


sealed class BottomSheetDialogEntity(
    open val textResId: Int? = null,
    open val imageResId: Int? = null,
    open val title: String? = null,
    open val image: String? = null,
    val textGravity: Int = Gravity.CENTER_HORIZONTAL,
    val isClickable: Boolean = true,
    val option: BottomSheetDialogOption
)

data class ChatOptionEntity(val chatOption: ChatOption) : BottomSheetDialogEntity(
    textResId = chatOption.textResId,
    imageResId = chatOption.imageResId,
    textGravity = Gravity.START,
    option = chatOption
)

enum class ChatSwipeTitleOption : BottomSheetDialogOption { PIN_TOGGLE, CLEAR, BLOCK }

data class TitleDialogEntity(
    val text: String? = null,
    val textId: Int? = null,
    val clickable: Boolean,
    val titleActionOption: ChatSwipeTitleOption
) :
    BottomSheetDialogEntity(
        textResId = textId,
        title = text,
        isClickable = clickable,
        option = titleActionOption
    )

interface BottomSheetDialogOption

enum class ChatOption(val textResId: Int, val imageResId: Int) : BottomSheetDialogOption {
    SEARCH_BY_CONVERSATION(R.string.search_by_conversation, R.drawable.ic_fi_search),
    INTERVIEW_MATERIALS(R.string.interview_materials, R.drawable.ic_fi_inbox),
    DISABLE_NOTIFICATIONS(R.string.disable_notifications, R.drawable.ic_bell),
    CLEAR_THE_HISTORY(R.string.clear_the_history, R.drawable.ic_clear_history),
    ADD_PARTICIPANT(R.string.add_participant, R.drawable.ic_u_plus),
}

enum class ChatAttachmentOption(val textResId: Int) {
    PHOTO_OR_VIDEO(R.string.photo_or_video),
    FILE(R.string.file),
    CONTACT(R.string.contact),
    INTERROGATION(R.string.interrogation),
    SEND_PHOTO(R.string.send_photos)
}

interface PopupWindowMenu {
    val textResId: Int
    val imageResId: Int
}

enum class ChatClickMenu(override val textResId: Int, override val imageResId: Int) :
    PopupWindowMenu {
    ANSWER(R.string.chat_message_popup_menu_answer, R.drawable.ic_popup_menu_answer),
    COPY(R.string.chat_message_popup_menu_copy, R.drawable.ic_popup_menu_copy),
    FORWARD(R.string.chat_message_popup_menu_forward, R.drawable.ic_popup_menu_forward),
    EDIT(R.string.chat_message_popup_menu_edit, R.drawable.ic_popup_menu_edit),
    PIN(R.string.chat_message_popup_menu_pin, R.drawable.ic_popup_menu_pin),
    DELETE(R.string.chat_message_popup_menu_delete, R.drawable.ic_vector_delete),
    SELECT(R.string.chat_message_popup_menu_select, R.drawable.ic_popup_menu_select),
}

enum class DialogLongPressMenu(override val textResId: Int, override val imageResId: Int) :
    PopupWindowMenu {
    PIN_CHAT(R.string.pin_chat, R.drawable.ic_pushpin),
    UNPIN_CHAT(R.string.unpin_chat, R.drawable.ic_pushpin),
    HIDE_CHAT(R.string.hide_chat, R.drawable.ic_fi_eye),
    DISABLE_NOTIFICATIONS(R.string.disable_notifications, R.drawable.ic_bell),
    CLEAR_THE_HISTORY(R.string.clear_the_history, R.drawable.ic_clear_history),
    BLOCK(R.string.swipe_block, R.drawable.ic_arrow_down_circle)
}