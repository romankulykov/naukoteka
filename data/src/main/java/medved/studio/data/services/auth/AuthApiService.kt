package medved.studio.data.services.auth

import io.reactivex.Single
import medved.studio.data.services.models.request.auth.AuthRequestDto
import medved.studio.data.services.models.request.auth.CheckEmailFreeDto
import medved.studio.data.services.models.request.auth.CheckTokenKeyDto
import medved.studio.data.services.models.request.auth.RegisterRequestDto
import medved.studio.data.services.models.response.auth.AuthResponseDto
import medved.studio.data.services.models.response.auth.CheckTokenKeyResponse
import medved.studio.data.services.models.response.auth.EmailFreeResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("login-actions/authenticate")
    fun login(@Body authRequestDto: AuthRequestDto): Single<AuthResponseDto>

    @POST("login-actions/register")
    fun register(@Body authRequestDto: RegisterRequestDto): Single<AuthResponseDto>

    @POST("login-actions/action-token")
    fun checkTokenKey(@Body checkTokenKey: CheckTokenKeyDto): Single<CheckTokenKeyResponse>

    @POST("login-actions/authenticate/check-email-free")
    fun checkEmailFree(@Body checkEmailFreeDto: CheckEmailFreeDto): Single<EmailFreeResponse>


}