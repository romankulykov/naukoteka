package uddug.com.data.services.models.response.dialogs

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


data class ChatMessageDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String?,
    @SerializedName("type")
    val type: Int,
    @SerializedName("files")
    val files: List<AttachmentChatPreviewDto>,
    @SerializedName("ownerId")
    val ownerId: String?,
    @SerializedName("createdAt")
    val createdAt: Calendar,
    @SerializedName("read")
    val read: ArrayList<LinkedTreeMap<String, Double>>
)