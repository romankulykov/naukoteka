package uddug.com.naukoteka.ui.fragments.chat_detail

open class Payload {
    var dropInActivity: IDropInActivity? = null
    var dropInChat: IDropInChat? = null
}

interface IDropInChat {
    fun droppedInChat(something: Any)
}

interface IDropInActivity {
    fun droppedInActivity(something: Any)
}