package medved.studio.data.services.auth

import io.reactivex.Single
import medved.studio.data.services.models.request.auth.AuthRequestDto
import medved.studio.data.services.models.response.auth.AuthResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("login-actions/authenticate")
    fun login(@Body authRequestDto: AuthRequestDto) : Single<AuthResponseDto>


}