package uddug.com.data.services

import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.PATCH
import uddug.com.data.services.models.request.messages.ReadMessageRequestDto

interface MessagesApiService {

    @PATCH("v1/messages/read")
    fun readMessage(@Body readMessageRequestDto: ReadMessageRequestDto): Completable

}