package uddug.com.domain.interactors.internal

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore
import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.domain.SchedulersProvider
import uddug.com.domain.entities.AndroidFileEntity

@InjectConstructor
class AndroidFilesInteractor(
    private val context: Context,
    private val schedulers: SchedulersProvider
) {

    @SuppressLint("Recycle")
    fun allFile(selection: String) = context.run {
        Single.fromCallable {
            val bunchOfFiles = mutableListOf<AndroidFileEntity>()
            val columns = arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE
            )
            val queryUri = MediaStore.Files.getContentUri("external")
            val mediaCursor = contentResolver?.query(
                queryUri,
                columns,
                selection,
                null,
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
            ) ?: return@fromCallable bunchOfFiles

            for (i in 0 until mediaCursor.count) {
                mediaCursor.moveToPosition(i)
                val path = mediaCursor.getString(
                    mediaCursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                )
                bunchOfFiles.add(
                    AndroidFileEntity(
                        path = path,
                        type = mediaCursor.getInt(mediaCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE))
                    )
                )
            }
            bunchOfFiles
        }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }
}