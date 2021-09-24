package medved.studio.pharmix.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import medved.studio.data.utils.fromJson
import medved.studio.domain.entities.ApiErrorDetail
import medved.studio.domain.entities.ApiErrorWrapper
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
    } catch (exceptAnotherModelException: JsonSyntaxException) {
        ApiErrorDetail(0, "", "", "Could not parse correct error")
    }
}