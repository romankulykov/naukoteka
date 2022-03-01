package uddug.com.domain.interactors.messages

import io.reactivex.Completable
import toothpick.InjectConstructor
import uddug.com.domain.SchedulersProvider
import uddug.com.domain.repositories.messages.MessagesRepository

@InjectConstructor
class MessagesInteractor(
    private val messagesRepository: MessagesRepository,
    private val schedulers: SchedulersProvider,
) {

    fun readMessages(dialogId: Int, messageIds: List<Int>): Completable {
        return messagesRepository.readMessage(dialogId, messageIds)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

}