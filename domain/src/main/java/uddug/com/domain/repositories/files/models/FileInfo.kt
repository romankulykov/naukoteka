package uddug.com.domain.repositories.files.models

data class FileInfo(
    val id: String,
    val path: String,
    val filename: String,
    val contentType: String?,
    val fileSize: String?,
    val fileType: String?
)