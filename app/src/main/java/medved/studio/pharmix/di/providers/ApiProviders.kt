package medved.studio.pharmix.di.providers

import medved.studio.data.services.auth.AuthApiHolder
import medved.studio.data.services.auth.AuthApiService
import retrofit2.Retrofit
import toothpick.InjectConstructor
import javax.inject.Provider


@InjectConstructor
class AuthApiProvider(
    private val retrofit: Retrofit,
    private val authApiHolder: AuthApiHolder,
) : Provider<AuthApiService> {
    override fun get() =
        retrofit.create(AuthApiService::class.java).also {
            authApiHolder.authApi = it
        }
}
