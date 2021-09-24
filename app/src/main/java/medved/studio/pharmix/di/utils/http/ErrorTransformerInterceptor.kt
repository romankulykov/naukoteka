package medved.studio.pharmix.di.utils.http

import medved.studio.domain.entities.HttpException
import medved.studio.domain.entities.ServerApiError
import medved.studio.domain.utils.logging.ILogger
import medved.studio.pharmix.utils.getApiError
import okhttp3.Interceptor
import okhttp3.Response
import toothpick.InjectConstructor

@InjectConstructor
class ErrorTransformerInterceptor constructor(
    private val logger: ILogger
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.run { proceed(request()) }
        if (response.code > 399) {
            response.getApiError().run {
                throw HttpException(ServerApiError.fromInt(code), message, null)
            }
        }

        return response
    }
}