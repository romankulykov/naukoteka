package medved.studio.data.services.models.response.auth

import com.google.gson.annotations.SerializedName

data class EmailFreeResponse(
    @SerializedName("emailIsFree")
    val emailIsFree: Boolean
)