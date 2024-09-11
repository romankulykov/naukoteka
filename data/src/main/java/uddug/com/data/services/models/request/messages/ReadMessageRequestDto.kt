package uddug.com.data.services.models.request.messages

import com.google.gson.annotations.SerializedName

data class ReadMessageRequestDto(
    @SerializedName("dialogId")
    val dialogId: Int,
    @SerializedName("messages")
    val messages: List<Int>,
    @SerializedName("readStatus")
    val readStatus: Int
)
