package medved.studio.pharmix.utils

import android.content.Intent

interface ActivityResultListener {
    fun onFragmentResult(requestCode: Int, resultCode: Int, data: Intent?)
}