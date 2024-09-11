package uddug.com.data.repositories.files

import android.webkit.MimeTypeMap
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import toothpick.InjectConstructor
import uddug.com.data.services.FilesApiService
import uddug.com.domain.repositories.files.FilesRepository
import uddug.com.domain.repositories.files.models.FileInfo
import java.io.File


@InjectConstructor
class FilesRepositoryImpl(
    private val filesApiService: FilesApiService,
    private val filesRepositoryMapper: FilesRepositoryMapper
) : FilesRepository {

    override fun sendFiles(files: List<File>): Single<List<FileInfo>> {
        val builder = MultipartBody.Builder()
        files.forEach { file ->
            builder.addFormDataPart(
                "files",
                file.name,
                RequestBody.create(MediaType.parse(getMimeType(file)), file.readBytes())
            )
        }
        return filesApiService.sendFiles(builder.build().parts())
            .map { it.map { filesRepositoryMapper.mapFilesToDomain(it) } }
    }

    private fun getMimeType(file: File): String {
        var type: String? = null
        val url = file.toString()
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.lowercase())
        }
        if (type == null) {
            type = "image/*" // fallback type. You might set it to */*
        }
        return type
    }

}