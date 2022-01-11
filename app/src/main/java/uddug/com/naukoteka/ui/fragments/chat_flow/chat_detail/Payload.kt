package uddug.com.naukoteka.ui.fragments.chat_flow.chat_detail

import androidx.lifecycle.MutableLiveData

open class Payload {
    var dropInActivity: IDropInActivity? = null
    var dropInChat: IDropInChat? = null
    val isMessagesSelected = MutableLiveData<Boolean>()
}

interface IDropInChat {
    fun droppedInChat(something: Any)
}

interface IDropInActivity {
    fun droppedInActivity(something: Any)
}