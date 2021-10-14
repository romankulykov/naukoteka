package medved.studio.data.services

import io.reactivex.Observable
import io.reactivex.Single
import medved.studio.data.services.models.request.user_profile.NickNameCheckRequestDto
import medved.studio.data.services.models.request.user_profile.UserProfileRequestDto
import medved.studio.data.services.models.response.auth.MeReposnseDto
import medved.studio.data.services.models.response.user_profile.CheckNickNameResponseDto
import medved.studio.data.services.models.response.user_profile.UserProfileDto
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