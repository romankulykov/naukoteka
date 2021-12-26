package uddug.com.data.services

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uddug.com.data.services.models.response.dialogs.ChatMessageDto
import uddug.com.data.services.models.response.dialogs.ChatPreviewResponseDto

interface DialogsApiService {

    @GET("v1/dialogs")
    fun dialogs(): Single<ChatPreviewResponseDto>

    @GET("v1/dialogs/{id}")
    fun dialogsDetail(
        @Path("id") id: Int,
        @Query("lastMessageId") lastMessageId: Int? = null,
        @Query("limit") limit: Int? = null,
    ): Single<List<ChatMessageDto>>

}