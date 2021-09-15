package medved.studio.pharmix.di.utils.http

import medved.studio.domain.utils.logging.ILogger
import medved.studio.pharmix.utils.getStatusCode
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
            val statusCode = response.getStatusCode()
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