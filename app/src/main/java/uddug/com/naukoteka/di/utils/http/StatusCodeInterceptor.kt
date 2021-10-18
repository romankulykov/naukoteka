package uddug.com.naukoteka.di.utils.http

import uddug.com.domain.utils.logging.ILogger
import uddug.com.naukoteka.utils.getApiError
import okhttp3.Interceptor
import okhttp3.Response
import toothpick.InjectConstructor

@InjectConstructor
class StatusCodeParsingInterceptor constructor(
    private val logger: ILogger
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.run { proceed(request()) }
        try {
            val statusCode = response.getApiError()?.code?:0
            logger.info("Parsed status code: $statusCode")

            response.javaClass.getDeclaredField("code").run {
                isAccessible = true
                set(response, statusCode)
            }
        } catch (e: Exception) {
            logger.error("Could not set parsed status code", e)
            return response
        }

        return response
    }
}