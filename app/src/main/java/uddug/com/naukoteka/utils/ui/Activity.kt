package uddug.com.naukoteka.utils.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import uddug.com.naukoteka.NaukotekaApplication
import uddug.com.naukoteka.utils.tryOrNull
import uddug.com.naukoteka.utils.tryTo
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun <F : Fragment> FragmentActivity.findFragment(tag: String): F? {
    return (supportFragmentManager.findFragmentByTag(tag) as? F)
}

private const val FILE_PROVIDER = "com.nauchat.fileprovider"

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

fun Activity.takePhoto(requestCode: Int) = createImageFile()?.let { takePhoto(requestCode, it) }

private fun Activity.takePhoto(requestCode: Int, photoFile: File): String? = tryOrNull {
    val context = NaukotekaApplication.context!!
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val photoUri: Uri = FileProvider.getUriForFile(context, FILE_PROVIDER, photoFile)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
    startActivityForResult(intent, requestCode)
    return photoFile.absolutePath
}

@SuppressLint("SimpleDateFormat")
private fun createImageFile(): File? = tryOrNull {
    val context = NaukotekaApplication.context!!
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}
