package uddug.com.naukoteka.di.providers

import retrofit2.Retrofit
import toothpick.InjectConstructor
import uddug.com.data.services.AuthApiService
import uddug.com.data.services.DialogsApiService
import uddug.com.data.services.UserProfileApiService
import uddug.com.data.services.auth.AuthApiHolder
import uddug.com.domain.repositories.users_search.UsersSearchRepository
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

@InjectConstructor
class UserProfileApiProvider(
    private val retrofit: Retrofit,
) : Provider<UserProfileApiService> {
    override fun get() =
        retrofit.create(UserProfileApiService::class.java)
}

@InjectConstructor
class DialogsApiProvider(
    private val retrofit: Retrofit,
) : Provider<DialogsApiService> {
    override fun get() =
        retrofit.create(DialogsApiService::class.java)
}

@InjectConstructor
class UsersSearchApiProvider(
    private val retrofit: Retrofit,
) : Provider<UsersSearchRepository> {
    override fun get() =
        retrofit.create(UsersSearchRepository::class.java)
}
