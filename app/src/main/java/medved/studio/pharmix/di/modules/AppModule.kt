package medved.studio.pharmix.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.gson.Gson
import medved.studio.data.repositories.auth.AuthRepositoryImpl
import medved.studio.data.services.auth.AuthApiHolder
import medved.studio.data.services.auth.AuthApiService
import medved.studio.domain.SchedulersProvider
import medved.studio.domain.repositories.auth.AuthRepository
import medved.studio.domain.utils.logging.ILogger
import medved.studio.pharmix.BuildConfig
import medved.studio.pharmix.di.ServerUrl
import medved.studio.pharmix.di.providers.*
import medved.studio.pharmix.di.utils.Logger
import medved.studio.pharmix.navigation.AppRouter
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import toothpick.config.Module

class AppModule(application: Application) : Module() {

    init {
        bind(Context::class.java).toInstance(application)
        bind(ILogger::class.java).to(Logger::class.java).singleton()
        bind(SharedPreferences::class.java).toProvider(SharedPreferencesProvider::class.java)
        bind(Gson::class.java).toProvider(GsonProvider::class.java).singleton()
        bind(SchedulersProvider::class.java).toInstance(AppSchedulersProvider())

        bind(String::class.java).withName(ServerUrl::class.java)
            .toInstance("https://dev.mypharmy.ru/api/")

        bind(OkHttpClient::class.java).toProvider(OkHttpProvider::class.java).singleton()
        bind(Retrofit::class.java).toProvider(RetrofitProvider::class.java).singleton()

        val cicerone = Cicerone.create(AppRouter())
        bind(AppRouter::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.getNavigatorHolder())


        bind(AuthApiHolder::class.java).singleton()
        bind(AuthApiService::class.java).toProvider(AuthApiProvider::class.java).singleton()
        bind(AuthRepository::class.java).to(AuthRepositoryImpl::class.java)

    }


}