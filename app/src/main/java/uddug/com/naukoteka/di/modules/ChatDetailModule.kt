package uddug.com.naukoteka.di.modules

import toothpick.config.Module
import uddug.com.domain.repositories.dialogs.models.ChatPreview

class ChatDetailModule(chatPreview: ChatPreview) : Module() {

    init {
        bind(ChatPreview::class.java).toInstance(chatPreview)
    }

}