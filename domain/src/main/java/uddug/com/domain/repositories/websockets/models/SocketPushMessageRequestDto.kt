package uddug.com.domain.repositories.websockets.models

import com.google.gson.annotations.SerializedName

data class SocketPushMessageRequestDto(
    @SerializedName("dialog")
    val dialog: Int,
    @SerializedName("cType")
    val cType: Int,
    @SerializedName("text")
    val text: String
)
