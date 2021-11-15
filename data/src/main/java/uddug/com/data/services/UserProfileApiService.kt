package uddug.com.data.services

import io.reactivex.Observable
import io.reactivex.Single
import uddug.com.data.services.models.request.user_profile.NickNameCheckRequestDto
import uddug.com.data.services.models.request.user_profile.UserProfileRequestDto
import uddug.com.data.services.models.response.user_profile.CheckNickNameResponseDto
import uddug.com.data.services.models.response.user_profile.UserProfileDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserProfileApiService {

    @POST("core/user_profile/info")
    fun setUser(
        //@Header("Cookie") _nkts: String? = null,
        //@Header(" _nkthnt") _nkthnt: String? = null,
        @Body userProfileRequestDto: UserProfileRequestDto
    ): Single<String>

    @GET("core/user_profile/info")
    fun getUserInfo(): Single<UserProfileDto>

    @POST("core/user_profile/info/check-nickname-free")
    fun checkNickName(
        @Body nickName: NickNameCheckRequestDto
    ): Observable<CheckNickNameResponseDto>

}