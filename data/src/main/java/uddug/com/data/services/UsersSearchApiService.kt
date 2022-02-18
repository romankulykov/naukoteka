package uddug.com.data.services

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import uddug.com.data.services.models.request.user_status.UserStatusRequestDto
import uddug.com.data.services.models.response.users_search.UsersSearchResponseDto
import uddug.com.data.services.models.response.users_status.UsersStatusResponseDto

interface UsersSearchApiService {

    @GET("v1/users/search")
    fun usersSearch(
        @Query("searchField") searchField: String,
        @Query("limit") limit: Int? = null,
    ): Single<UsersSearchResponseDto>

    @POST("v1/users/status")
    fun userStatus(@Body users: UserStatusRequestDto) : Single<List<UsersStatusResponseDto>>

}