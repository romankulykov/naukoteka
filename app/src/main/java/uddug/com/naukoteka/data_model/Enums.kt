package uddug.com.naukoteka.data_model

import uddug.com.naukoteka.R

enum class ChatOption(val textResId: Int, val imageResId: Int) {
    SEARCH_BY_CONVERSATION(R.string.search_by_conversation, R.drawable.ic_fi_search),
    INTERVIEW_MATERIALS(R.string.interview_materials, R.drawable.ic_fi_inbox),
    DISABLE_NOTIFICATIONS(R.string.disable_notifications, R.drawable.ic_bell),
    CLEAR_THE_HISTORY(R.string.clear_the_history, R.drawable.ic_clear_history),
    ADD_PARTICIPANT(R.string.add_participant, R.drawable.ic_u_plus),
}

enum class ChatLongPressMenu(val textResId: Int, val imageResId: Int) {
    ANCHOR_CHAT(R.string.anchor_chat, R.drawable.ic_pushpin),
    HIDE_CHAT(R.string.hide_chat, R.drawable.ic_fi_eye),
    DISABLE_NOTIFICATIONS(R.string.disable_notifications, R.drawable.ic_bell),
    CLEAR_THE_HISTORY(R.string.clear_the_history, R.drawable.ic_clear_history),
    BLOCK(R.string.swipe_block, R.drawable.ic_arrow_down_circle)
}

enum class ChatAttachmentOption(val textResId: Int) {
    PHOTO_OR_VIDEO(R.string.photo_or_video),
    FILE(R.string.file),
    CONTACT(R.string.contact),
    INTERROGATION(R.string.interrogation)
}