package uddug.com.data.services.models.request.dialogs

import com.google.gson.annotations.SerializedName

data class CreateDialogRequestDto(
    @SerializedName("dialogName")
    val dialogName: String? = null,
    @SerializedName("users")
    val users: List<String>
)
