package uddug.com.data.services

import io.reactivex.Single
import retrofit2.http.*
import uddug.com.data.services.models.request.dialogs.CreateDialogRequestDto
import uddug.com.data.services.models.response.dialogs.ChatMessageDto
import uddug.com.data.services.models.response.dialogs.ChatPreviewDto
import uddug.com.data.services.models.response.dialogs.ChatPreviewResponseDto
import uddug.com.data.services.models.response.dialogs.CreateDialogResponseDto

interface DialogsApiService {

    @GET("v1/dialogs")
    fun dialogs(): Single<ChatPreviewResponseDto>

    @GET("v1/dialogs/{id}")
    fun dialogsDetail(
        @Path("id") id: Int,
        @Query("lastMessageId") lastMessageId: Int? = null,
        @Query("limit") limit: Int? = null,
    ): Single<List<ChatMessageDto>>

    @GET("v1/dialogs/info/{id}")
    fun getChatDetailInfo(
        @Path("id") id: Int
    ): Single<ChatPreviewDto>

    @POST("v1/dialogs/create")
    fun dialogCreate(
        @Body createDialogRequest: CreateDialogRequestDto? = null,
    ): Single<CreateDialogResponseDto>

}