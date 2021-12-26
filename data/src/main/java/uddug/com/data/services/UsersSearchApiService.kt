package uddug.com.data.services

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import uddug.com.data.services.models.response.dialogs.ChatMessageDto
import uddug.com.data.services.models.response.users_search.UsersSearchResponseDto

interface UsersSearchApiService {

    @GET("v1/users/search")
    fun usersSearch(
        @Query("searchField") searchField: String,
        @Query("limit") limit: Int? = null,
    ): Single<UsersSearchResponseDto>

}