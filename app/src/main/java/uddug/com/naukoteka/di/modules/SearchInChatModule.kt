package uddug.com.naukoteka.di.modules

import toothpick.config.Module

class SearchInChatModule(dialogId: Int? = null) : Module() {

    init {
        bind(SearchInChatParams::class.java).toInstance(SearchInChatParams(dialogId))
    }

    data class SearchInChatParams(val dialogId: Int? = null)

}