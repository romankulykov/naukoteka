package medved.studio.pharmix.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import medved.studio.domain.repositories.auth.models.SocialType

object IntentKeys {

    @Parcelize
    data class Registration(val key: String) : Parcelable {
        companion object {
            const val KEY = "registration"
        }
    }

    @Parcelize
    data class SocialAuthorization(val key: String, val socialType: SocialType) : Parcelable {
        companion object {
            const val KEY = "social-auth"
        }
    }


}