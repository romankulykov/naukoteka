package medved.studio.data.services.models.request.auth

import com.google.gson.annotations.SerializedName

data class CheckTokenKeyDto(
    @SerializedName("key")
    val key: String
)