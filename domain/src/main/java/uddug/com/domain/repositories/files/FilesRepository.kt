package uddug.com.domain.repositories.files

import io.reactivex.Single
import uddug.com.domain.repositories.files.models.FileInfo
import java.io.File

interface FilesRepository {

    fun sendFiles(files: List<File>): Single<List<FileInfo>>

}