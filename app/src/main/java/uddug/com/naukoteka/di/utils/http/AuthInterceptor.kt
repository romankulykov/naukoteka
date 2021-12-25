package uddug.com.naukoteka.di.utils.http

import okhttp3.Interceptor
import toothpick.InjectConstructor
import uddug.com.data.cache.token.UserTokenCache

@InjectConstructor
class AuthInterceptor(private val userTokenCache: UserTokenCache) : Interceptor {

    private val accessToken get() = userTokenCache.entity

    override fun intercept(chain: Interceptor.Chain) = chain.run {
        val bearerToken = request().header("Authorization") ?: "Bearer $accessToken"
        proceed(
            request().newBuilder()
                .removeHeader("Authorization")
                .addHeader("Authorization", bearerToken)
                .build()
        )
    }
}
