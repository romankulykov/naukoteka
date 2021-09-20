package medved.studio.pharmix.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

object IntentKeys {

    @Parcelize
    data class Registration(val key: String) : Parcelable {
        companion object {
            const val KEY = "registration"
        }
    }


}