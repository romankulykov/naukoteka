package uddug.com.naukoteka.utils

import com.google.gson.Gson
import uddug.com.data.utils.fromJson
import uddug.com.domain.entities.ApiErrorDetail
import uddug.com.domain.entities.ApiErrorWrapper
import uddug.com.domain.entities.StatusCode
import okhttp3.FormBody
import okhttp3.Response
import java.nio.charset.Charset

inline fun Any.formBody(func: FormBody.Builder.() -> FormBody.Builder): FormBody {
    return FormBody.Builder().func().build()
}

/*fun Response.getStatusCode(): Int {
    val responseBody = body!!
    val source = responseBody.source().also { it.request(Long.MAX_VALUE) }
    val responseBodyString = source.buffer().clone().readString(Charset.forName("UTF-8"))

    return Gson().fromJson<ApiResponse<Any>>(responseBodyString).statusCode.code
}*/

fun Response.getApiError(): ApiErrorDetail {
    val responseBody = body!!
    val source = responseBody.source().also { it.request(Long.MAX_VALUE) }
    val responseBodyString = source.buffer().clone().readString(Charset.forName("UTF-8"))
    return try {
        Gson().fromJson<ApiErrorWrapper>(responseBodyString).apiErrorDetail
    } catch (npe: Exception) {
        val message = when (StatusCode.values().find { it.code == code }) {
            StatusCode.Unauthorized -> "Unauthorized method"
            StatusCode.NoConnection -> "No connection"
            StatusCode.BadRequest -> "Bad request"
            StatusCode.Forbidden -> "Forbidden method"
            StatusCode.NotFound -> "NotFound method"
            StatusCode.InternalServerError -> "Internal Server Error"
            else -> "Could not found status code error"
        }
        ApiErrorDetail(code, "", "", message)
    }
}