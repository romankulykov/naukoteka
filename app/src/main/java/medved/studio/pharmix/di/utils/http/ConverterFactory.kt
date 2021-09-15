package medved.studio.pharmix.di.utils.http

import com.google.gson.Gson
import medved.studio.domain.entities.ApiResponse
import medved.studio.domain.entities.HttpException
import medved.studio.domain.entities.HttpRequestException
import medved.studio.domain.entities.StatusCode
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import toothpick.InjectConstructor
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@InjectConstructor
class ApiResponseConverterFactory(
    gson: Gson
) : Converter.Factory() {
    private val gsonConverterFactory = GsonConverterFactory.create(gson)

    @Suppress("UNCHECKED_CAST")
    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val wrappedType = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
            override fun getOwnerType(): Type? = null
            override fun getRawType(): Type = ApiResponse::class.java
        }

        val gsonConverter =
            gsonConverterFactory.responseBodyConverter(wrappedType, annotations, retrofit)
        return ResponseBodyConverter<ApiResponse<Any>>(gsonConverter as Converter<ResponseBody, ApiResponse<Any>>)
    }

    override fun requestBodyConverter(
        type: Type?,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return gsonConverterFactory.requestBodyConverter(
            type!!,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

    private class ResponseBodyConverter<T>(private val converter: Converter<ResponseBody, ApiResponse<Any>>) :
        Converter<ResponseBody, T> {
        @Throws(IOException::class)
        override fun convert(responseBody: ResponseBody): T {
            val response = converter.convert(responseBody)!!
            return try {
                if (response.statusCode == StatusCode.Ok) {
                    response.data as T
                } else {
                    throw HttpException(
                        response.statusCode,
                        response.exception ?: response.reason!!,
                        response.reason
                    )
                }
            } catch (e: Exception) {
                if (e is HttpException) {
                    throw e
                }

                throw HttpRequestException(e)
            }
        }
    }
}