package uddug.com.naukoteka.utils.net

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import uddug.com.naukoteka.NaukotekaApplication

private val Uri.isExternalStorageDocument get() = "com.android.externalstorage.documents" == authority
private val Uri.isDownloadsDocument get() = "com.android.providers.downloads.documents" == authority
private val Uri.isMediaDocument get() = "com.android.providers.media.documents" == authority

fun Uri.extractPath(): String? {

    val context = NaukotekaApplication.context!!

    if (DocumentsContract.isDocumentUri(context, this)) {
        if (isExternalStorageDocument) {
            val docId = DocumentsContract.getDocumentId(this)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]
            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            } else {
                val wholeID = DocumentsContract.getDocumentId(this)
                val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                val column = arrayOf(MediaStore.Images.Media.DATA)
                val sel = MediaStore.Images.Media._ID + "=?"
                val cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, arrayOf(id), null)
                var filePath = ""
                if (cursor != null) {
                    val columnIndex = cursor.getColumnIndex(column[0])
                    if (cursor.moveToFirst()) {
                        filePath = cursor.getString(columnIndex)
                    }
                    cursor.close()
                }
                return filePath
            }
        } else if (isDownloadsDocument) {
            val id = DocumentsContract.getDocumentId(this)
            try {
                if (id.startsWith("raw"))
                    return id.replace("raw:", "")
                val contentUriPrefixesToTry = arrayOf(
                    "content://downloads/public_downloads",
                    "content://downloads/my_downloads",
                    "content://downloads/all_downloads"
                )
                for (contentUriPrefix in contentUriPrefixesToTry) {
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse(contentUriPrefix),
                        java.lang.Long.valueOf(id)
                    )
                    try {
                        val path = contentUri.getDataColumn(  null, null)
                        if (path != null) {
                            return path
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.parseLong(id))
                return contentUri.getDataColumn(null, null)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return null
            }

        } else if (isMediaDocument) {
            val docId = DocumentsContract.getDocumentId(this)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]
            val contentUri = when (type) {
                "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                else -> null
            }
            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])
            return contentUri?.getDataColumn(selection, selectionArgs)
        }
    } else if ("content".equals(scheme, ignoreCase = true)) {
        return getDataColumn(null, null)
    } else if ("file".equals(scheme, ignoreCase = true)) {
        return path
    }
    return null
}

private fun Uri.getDataColumn(selection: String?, selectionArgs: Array<String>?): String? {
    val context = NaukotekaApplication.context!!
    var cursor: Cursor? = null
    val column = MediaStore.Images.Media.DATA//"_data";
    val projection = arrayOf(column)
    try {
        cursor = context.contentResolver.query(this, projection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(column)
            return if (columnIndex < 0) null else cursor.getString(columnIndex)
        }
    }catch (e : Exception){
        //context.toast(R.string.something_went_wrong)
        //e.printStackTrace()
    } finally {
        cursor?.close()
    }
    return null
}
