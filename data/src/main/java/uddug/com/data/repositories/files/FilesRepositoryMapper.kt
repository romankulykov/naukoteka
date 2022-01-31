package uddug.com.data.repositories.files

import toothpick.InjectConstructor
import uddug.com.data.services.models.response.files.FileInfoResponseDto
import uddug.com.domain.repositories.files.models.FileInfo

@InjectConstructor
class FilesRepositoryMapper {

    fun mapFilesToDomain(dto: FileInfoResponseDto): FileInfo =
        dto.run {
            FileInfo(
                id = id,
                path = path,
                filename = filename,
                contentType = contentType,
                fileSize = fileSize,
                fileType = fileType
            )
        }

}