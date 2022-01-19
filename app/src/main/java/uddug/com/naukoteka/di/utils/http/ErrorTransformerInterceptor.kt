package uddug.com.naukoteka.di.utils.http

import okhttp3.Interceptor
import okhttp3.Response
import toothpick.InjectConstructor
import uddug.com.domain.entities.HttpException
import uddug.com.domain.entities.ServerApiError
import uddug.com.domain.utils.logging.ILogger
import uddug.com.naukoteka.utils.getApiError

@InjectConstructor
class ErrorTransformerInterceptor constructor(
    private val logger: ILogger
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.run { proceed(request()) }
        if (response.code > 399) {
            response.getApiError().run {
                ServerApiError.fromInt(code)?.let { apiError ->
                    throw HttpException(apiError, message, null)
                }
            }
        }

        return response
    }
}