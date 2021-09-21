package medved.studio.data.services.models.response.auth

import com.google.gson.annotations.SerializedName

data class EmailFreeResponseDto(
    @SerializedName("emailIsFree")
    val emailIsFree: Boolean
)