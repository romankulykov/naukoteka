package uddug.com.naukoteka.di.providers

import uddug.com.data.NaukotekaCookieJar
import uddug.com.naukoteka.BuildConfig
import uddug.com.naukoteka.di.utils.http.AuthInterceptor
import uddug.com.naukoteka.di.utils.http.ErrorTransformerInterceptor
import uddug.com.naukoteka.di.utils.http.RefreshTokenAuthenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import toothpick.InjectConstructor
import java.util.concurrent.TimeUnit
import javax.inject.Provider

@InjectConstructor
class OkHttpProvider(
    private val authInterceptor: AuthInterceptor,
    //private val statusCodeInterceptor: StatusCodeParsingInterceptor,
    private val errorTransformerInterceptor : ErrorTransformerInterceptor,
    private val authenticator: RefreshTokenAuthenticator,
    private val cookieJar: NaukotekaCookieJar,
    ) : Provider<OkHttpClient> {
    override fun get() = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        //.addNetworkInterceptor(statusCodeInterceptor)
        .addNetworkInterceptor(errorTransformerInterceptor)
        .addInterceptor(authInterceptor)
        .authenticator(authenticator)
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .build()
}