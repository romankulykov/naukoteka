package uddug.com.naukoteka.utils

import com.google.gson.Gson
import uddug.com.data.utils.fromJson
import uddug.com.domain.entities.ApiErrorDetail
import uddug.com.domain.entities.ApiErrorAuthWrapper
import uddug.com.domain.entities.StatusCode
import okhttp3.FormBody
import okhttp3.Response
import uddug.com.domain.entities.ResponseData
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
        val apiErrorAuth = Gson().fromJson<ApiErrorAuthWrapper>(responseBodyString)
        if (apiErrorAuth.apiErrorDetail != null) {
            apiErrorAuth.apiErrorDetail
        } else {
            val apiError = Gson().fromJson<ResponseData>(responseBodyString)
            if (apiError != null) {
                ApiErrorDetail(
                    apiError.data.statusCode,
                    apiError.data.message,
                    "",
                    apiError.data.message
                )
            } else throw Exception("Couldn't parse exception because error body not match app format")
        }
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