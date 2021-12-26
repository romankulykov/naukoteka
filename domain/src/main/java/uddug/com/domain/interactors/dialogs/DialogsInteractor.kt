package uddug.com.domain.interactors.dialogs

import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.domain.SchedulersProvider
import uddug.com.domain.repositories.dialogs.DialogsRepository
import uddug.com.domain.repositories.dialogs.models.ChatMessage
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.ChatsPreview

@InjectConstructor
class DialogsInteractor(
    private val dialogsRepository: DialogsRepository,
    private val schedulers: SchedulersProvider
) {

    fun getDialogs(): Single<ChatsPreview> {
        return dialogsRepository.getChatsPreview()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun getDialogMessages(chatPreview: ChatPreview): Single<List<ChatMessage>> {
        return dialogsRepository.getChatDetail(chatPreview)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

}