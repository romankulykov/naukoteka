package uddug.com.data.services

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import uddug.com.data.services.models.request.dialogs.CreateDialogRequestDto
import uddug.com.data.services.models.response.dialogs.*

interface DialogsApiService {

    @GET("v1/dialogs")
    fun dialogs(
        @Query("limit") limit: Int? = null,
        @Query("lastMessageId") lastMessageId: Int? = null,
    ): Single<ChatPreviewResponseDto>

    @GET("v1/dialogs/search")
    fun searchDialogs(
        @Query("searchField") searchField: String,
        @Query("limit") limit: Int? = null,
    ): Single<List<SearchDialogResponseDto>>

    @GET("v1/dialogs/media/{dialogId}")
    fun searchMedia(
        @Path("dialogId") dialogId: Int,
        @Query("category") category: Int,
        @Query("lastMessageId") lastMessageId: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("mediaClasses") mediaClasses: List<Int>? = null,
    ): Single<List<SearchMediaResponseDto>>

    @GET("v1/dialogs/search-messages/{dialogId}")
    fun searchMessagesInDialog(
        @Path("dialogId") id: Int,
        @Query("searchField") searchField: String,
        @Query("limit") limit: Int? = null,
    ): Single<List<SearchChatMessageDto>>

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

    @DELETE("v1/dialogs/{id}")
    fun deleteDialog(@Path("id") dialogId: Int): Completable

    @POST("v1/dialogs/pin/{id}")
    fun pinDialog(@Path("id") dialogId: Int): Completable

    @POST("v1/dialogs/unpin/{id}")
    fun unpinDialog(@Path("id") dialogId: Int): Completable

    @DELETE("v1/dialogs/clear/{dialogId}")
    fun clearDialog(@Path("dialogId") dialogId: Int): Completable

}