package medved.studio.pharmix.di.providers

import medved.studio.pharmix.di.ServerUrl
import medved.studio.pharmix.di.utils.http.ApiResponseConverterFactory
import medved.studio.pharmix.di.utils.http.EnumConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import toothpick.InjectConstructor
import javax.inject.Provider

@InjectConstructor
class RetrofitProvider(
    @ServerUrl private val serverUrl: String,
    private val okHttpClient: OkHttpClient,
    private val responseConverterFactory: ApiResponseConverterFactory,
) : Provider<Retrofit> {
    override fun get(): Retrofit =
        Retrofit.Builder()
            .baseUrl(serverUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(responseConverterFactory)
            .addConverterFactory(EnumConverterFactory())
            .build()
}