package uddug.com.naukoteka.utils.ui

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import uddug.com.naukoteka.utils.tryTo

fun <F : Fragment> FragmentActivity.findFragment(tag: String): F? {
    return (supportFragmentManager.findFragmentByTag(tag) as? F)
}

fun Activity.chooseFromStorage(requestCode: Int) = tryTo {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "*/*"
    intent.addCategory(Intent.CATEGORY_OPENABLE)

    val sIntent = Intent("com.sec.android.app.myfiles.PICK_DATA")
    sIntent.putExtra("CONTENT_TYPE", "*/*")
    sIntent.addCategory(Intent.CATEGORY_DEFAULT)
    val mimetypes = arrayOf("image/*", "video/*", "application/pdf")
    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
    //sIntent.addCategory(Intent.ACTION_OPEN_DOCUMENT)
    val chooserIntent: Intent
    if (packageManager.resolveActivity(sIntent, 0) != null) {
        // it is device with Samsung file manager
        chooserIntent = Intent.createChooser(sIntent, "Open file")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(Intent()))
    } else {
        chooserIntent = Intent.createChooser(intent, "Open file")
    }
    startActivityForResult(chooserIntent, requestCode)
}