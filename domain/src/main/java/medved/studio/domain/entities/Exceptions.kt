package medved.studio.domain.entities

import java.io.IOException

open class AcceptableException(override val message: String) : Exception(message)
open class UsualException(override val message: String) : Exception(message)
open class WtfException(override val message: String) : Exception(message)

data class HttpException(
    val statusCode: ServerApiError?,
    override val message: String,
    val reason: String?
) : UsualException(message)

class HttpRequestException(
    throwable: Throwable
) : UsualException("Something wrong happened during http request:\n${throwable.message}")

fun Throwable.shouldBeReported() = this !is AcceptableException && this !is IOException