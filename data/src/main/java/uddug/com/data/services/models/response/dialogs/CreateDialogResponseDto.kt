package uddug.com.data.services.models.response.dialogs

import com.google.gson.annotations.SerializedName

data class CreateDialogResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("isAlreadyExist")
    val isAlreadyExist: Boolean? = null
)
