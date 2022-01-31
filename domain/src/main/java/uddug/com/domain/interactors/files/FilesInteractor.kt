package uddug.com.domain.interactors.files

import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.domain.SchedulersProvider
import uddug.com.domain.repositories.files.FilesRepository
import uddug.com.domain.repositories.files.models.FileInfo
import java.io.File

@InjectConstructor
class FilesInteractor(
    private val filesRepository: FilesRepository,
    private val schedulers: SchedulersProvider
) {

    fun sendFiles(files: List<File>): Single<List<FileInfo>> {
        return filesRepository.sendFiles(files)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

}