package uddug.com.data.repositories.messages

import io.reactivex.Completable
import toothpick.InjectConstructor
import uddug.com.data.services.MessagesApiService
import uddug.com.data.services.models.request.messages.ReadMessageRequestDto
import uddug.com.domain.repositories.messages.MessagesRepository

@InjectConstructor
class MessagesRepositoryImpl(
    private val messagesApiService: MessagesApiService
) : MessagesRepository {

    override fun readMessage(dialogId: Int, messageIds: List<Int>): Completable {
        val readStatus = 3
        return messagesApiService.readMessage(
            ReadMessageRequestDto(
                dialogId,
                messageIds,
                readStatus
            )
        )
    }

}