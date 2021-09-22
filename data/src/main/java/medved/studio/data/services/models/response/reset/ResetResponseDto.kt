package medved.studio.data.services.models.response.reset


import com.google.gson.annotations.SerializedName

data class ResetResponseDto(
    @SerializedName("error")
    val error: Error
)

data class Error(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("textCode")
    val textCode: String,
    @SerializedName("type")
    val type: String
)
