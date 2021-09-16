package medved.studio.domain.entities

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    val data: T,
    @SerializedName("error") val error: ApiErrorDetail?
)

enum class StatusCode(val code: Int) {
    @SerializedName("0")
    NoConnection(0),

    @SerializedName("200")
    Ok(200),

    @SerializedName("400")
    BadRequest(400),

    @SerializedName("401")
    Unauthorized(401),

    @SerializedName("403")
    Forbidden(403),

    @SerializedName("404")
    NotFound(404),

    @SerializedName("409")
    CodeAlreadyUsed(404),

    @SerializedName("500")
    InternalServerError(500)
}

enum class ServerApiError(val code: Int) {
    @SerializedName("0")
    NoConnection(0),

    @SerializedName("200")
    Ok(200),

    @SerializedName("400")
    BadRequest(400),

    @SerializedName("401")
    Unauthorized(401),

    @SerializedName("403")
    Forbidden(403),

    @SerializedName("404")
    NotFound(404),

    @SerializedName("409")
    CodeAlreadyUsed(404),

    @SerializedName("500")
    InternalServerError(500)
}

data class ListResponseDto<T>(
    @SerializedName("count") val count: Int,
    @SerializedName("elements") val elements: List<T>
)

data class ListResponse<T>(val count: Int, val elements: List<T>)

data class ApiErrorWrapper(
    @SerializedName("error")
    val apiErrorDetail: ApiErrorDetail
)

data class ApiErrorDetail(
    @SerializedName("code")
    val code: Int,
    @SerializedName("textCode")
    val textCode: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("message")
    val message: String
)