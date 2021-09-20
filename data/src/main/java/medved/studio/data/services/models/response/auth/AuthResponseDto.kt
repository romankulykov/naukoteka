package medved.studio.data.services.models.response.auth

import com.google.gson.annotations.SerializedName


data class AuthResponseDto(
    @SerializedName("userStatus")
    val userStatus: UserStatusDto
)

data class UserStatusDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("enabled")
    val enabled: Boolean,
    @SerializedName("userActions")
    val userActions: UserActions?
)

data class UserActions(
    @SerializedName("actions")
    val actions: List<ActionDto>
)

data class ActionDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("uri")
    val uri: Any? = null
)