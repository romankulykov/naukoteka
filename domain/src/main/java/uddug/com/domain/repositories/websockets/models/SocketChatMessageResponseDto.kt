package uddug.com.domain.repositories.websockets.models

import com.google.gson.annotations.SerializedName
// {"id":9554,"text":"kkol","dialog":898,"files":[{"id":"1ff734f2-73fb-4832-9313-bb7405681d73","path":"\/s\/Messages\/9554\/file_default\/1ff734f2-73fb-4832-9313-bb7405681d73.jpg","filename":"Screenshot_20220217-232436_Aviasales.jpg","contenttype":"image\/jpeg","filetype":100}],"owner":"6ec2a2ab-84ab-4f8b-a781-af4f4afed578","cType":1}
data class SocketChatMessageResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("dialog")
    val dialog: Int,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("cType")
    val cType: Int,
    @SerializedName("files")
    val files: List<SocketMessageFileResponseDto>?,
)

data class SocketMessageFileResponseDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("filename")
    val filename: String,
    @SerializedName("contenttype")
    val contenttype: String,
    @SerializedName("filetype")
    val filetype: Int
)

data class SocketReadMessageResponseDto(
    @SerializedName("dialog")
    val dialog: Int,
    @SerializedName("action")
    val action: ActionResponseDto?
)

// {"dialog":898,"action":{"type":"read","messages":[9550]}}
data class ActionResponseDto(
    @SerializedName("type")
    val type: String,
    @SerializedName("messages")
    val messages: List<Int>
)