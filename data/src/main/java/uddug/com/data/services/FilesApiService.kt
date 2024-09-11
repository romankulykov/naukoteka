package uddug.com.data.services

import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import uddug.com.data.services.models.response.files.FileInfoResponseDto

interface FilesApiService {

    @Multipart
    @POST("core/files")
    fun sendFiles(
        @Part files: List<MultipartBody.Part>?
    ): Single<List<FileInfoResponseDto>>

}