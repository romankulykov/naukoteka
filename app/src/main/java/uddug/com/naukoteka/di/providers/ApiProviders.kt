package uddug.com.naukoteka.di.providers

import retrofit2.Retrofit
import toothpick.InjectConstructor
import uddug.com.data.services.*
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
) : Provider<UsersSearchApiService> {
    override fun get() =
        retrofit.create(UsersSearchApiService::class.java)
}

@InjectConstructor
class FilesApiServiceProvider(
    private val retrofit: Retrofit,
) : Provider<FilesApiService> {
    override fun get() =
        retrofit.create(FilesApiService::class.java)
}

@InjectConstructor
class MessagesApiServiceProvider(
    private val retrofit: Retrofit,
) : Provider<MessagesApiService> {
    override fun get() =
        retrofit.create(MessagesApiService::class.java)
}
