package uddug.com.data.repositories.files

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
                RequestBody.create(MediaType.parse("image/jpeg"), file.readBytes())
            )
        }
        return filesApiService.sendFiles(builder.build().parts())
            .map { it.map { filesRepositoryMapper.mapFilesToDomain(it) } }
    }

}