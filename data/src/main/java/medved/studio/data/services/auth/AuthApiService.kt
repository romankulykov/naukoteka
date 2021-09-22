package medved.studio.data.services.auth

import io.reactivex.Completable
import io.reactivex.Single
import medved.studio.data.services.models.request.auth.AuthRequestDto
import medved.studio.data.services.models.request.auth.CheckEmailFreeDto
import medved.studio.data.services.models.request.auth.CheckTokenKeyDto
import medved.studio.data.services.models.request.auth.RegisterRequestDto
import medved.studio.data.services.models.request.reset.ResetRequestDto
import medved.studio.data.services.models.response.auth.AuthResponseDto
import medved.studio.data.services.models.response.auth.CheckTokenKeyResponseDto
import medved.studio.data.services.models.response.auth.EmailFreeResponseDto
import medved.studio.data.services.models.response.auth.SocialTypesResponseDto
import medved.studio.data.services.models.response.reset.ResetResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {

    @POST("login-actions/authenticate")
    fun login(@Body authRequestDto: AuthRequestDto): Single<AuthResponseDto>

    @POST("login-actions/register")
    fun register(@Body authRequestDto: RegisterRequestDto): Single<AuthResponseDto>

    @POST("login-actions/action-token")
    fun checkTokenKey(@Body checkTokenKey: CheckTokenKeyDto): Single<CheckTokenKeyResponseDto>

    @POST("login-actions/authenticate/check-email-free")
    fun checkEmailFree(@Body checkEmailFreeDto: CheckEmailFreeDto): Single<EmailFreeResponseDto>

    @GET("login-actions/authenticate/idp-hints")
    fun getSocialTypes(): Single<SocialTypesResponseDto>

    @POST("login-actions/authenticate")
    fun authenticate(@Body authRequestDto: AuthRequestDto): Single<AuthResponseDto>

    @POST ("login-actions/execute")
    fun passwordRecovery(@Body resetRequestDto: ResetRequestDto) : Completable

}