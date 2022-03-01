package uddug.com.domain.repositories.messages

import io.reactivex.Completable

interface MessagesRepository {

    fun readMessage(dialogId: Int, messageIds: List<Int>): Completable

}