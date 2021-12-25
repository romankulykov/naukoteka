package uddug.com.data.services

import io.reactivex.Single
import retrofit2.http.GET
import uddug.com.data.services.models.response.dialogs.ChatPreviewResponseDto

interface DialogsApiService {

    @GET("v1/dialogs")
    fun dialogs() : Single<ChatPreviewResponseDto>

}