package medved.studio.data.services.models.request.auth


import com.google.gson.annotations.SerializedName

data class ResetRequestDto(
    @SerializedName("actionType")
    val actionType: String,
    @SerializedName("username")
    val userName: String
)