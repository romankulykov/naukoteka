package uddug.com.naukoteka.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.gson.Gson
import medved.studio.domain.repositories.session.SessionRepository
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import toothpick.config.Module
import uddug.com.data.NaukotekaCookieJar
import uddug.com.data.repositories.auth.AuthRepositoryImpl
import uddug.com.data.repositories.dialogs.DialogsRepositoryImpl
import uddug.com.data.repositories.files.FilesRepositoryImpl
import uddug.com.data.repositories.session.SessionRepositoryImpl
import uddug.com.data.repositories.user_profile.UserProfileRepositoryImpl
import uddug.com.data.repositories.users.UsersSearchRepositoryImpl
import uddug.com.data.repositories.websockets.WebSocketRepositoryImpl
import uddug.com.data.services.*
import uddug.com.data.services.auth.AuthApiHolder
import uddug.com.domain.SchedulersProvider
import uddug.com.domain.repositories.auth.AuthRepository
import uddug.com.domain.repositories.dialogs.DialogsRepository
import uddug.com.domain.repositories.files.FilesRepository
import uddug.com.domain.repositories.user_profile.UserProfileRepository
import uddug.com.domain.repositories.users_search.UsersSearchRepository
import uddug.com.domain.repositories.websockets.WebSocketRepository
import uddug.com.domain.utils.logging.ILogger
import uddug.com.naukoteka.di.ServerUrl
import uddug.com.naukoteka.di.providers.*
import uddug.com.naukoteka.di.utils.Logger
import uddug.com.naukoteka.navigation.AppRouter

class AppModule(application: Application) : Module() {

    init {
        bind(Context::class.java).toInstance(application)
        bind(ILogger::class.java).to(Logger::class.java).singleton()
        bind(SharedPreferences::class.java).toProvider(SharedPreferencesProvider::class.java)
        bind(Gson::class.java).toProvider(GsonProvider::class.java).singleton()
        bind(SchedulersProvider::class.java).toInstance(AppSchedulersProvider())

        bind(String::class.java).withName(ServerUrl::class.java)
            .toInstance("https://stage.naukotheka.ru/api/")
        bind(CookieJar::class.java).to(NaukotekaCookieJar::class.java)

        bind(SessionRepository::class.java).to(SessionRepositoryImpl::class.java)
        bind(OkHttpClient::class.java).toProvider(OkHttpProvider::class.java).singleton()
        bind(Retrofit::class.java).toProvider(RetrofitProvider::class.java).singleton()

        val cicerone = Cicerone.create(AppRouter())
        bind(AppRouter::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.getNavigatorHolder())


        bind(AuthApiService::class.java).toProvider(AuthApiProvider::class.java).singleton()
        bind(UserProfileApiService::class.java).toProvider(UserProfileApiProvider::class.java)
            .singleton()
        bind(DialogsApiService::class.java).toProvider(DialogsApiProvider::class.java).singleton()
        bind(UsersSearchApiService::class.java).toProvider(UsersSearchApiProvider::class.java).singleton()
        bind(AuthApiHolder::class.java).singleton()
        bind(AuthRepository::class.java).to(AuthRepositoryImpl::class.java)
        bind(UserProfileRepository::class.java).to(UserProfileRepositoryImpl::class.java)
        bind(DialogsRepository::class.java).to(DialogsRepositoryImpl::class.java)
        bind(FilesApiService::class.java).toProvider(FilesApiServiceProvider::class.java).singleton()
        bind(FilesRepository::class.java).to(FilesRepositoryImpl::class.java)
        bind(UsersSearchRepository::class.java).to(UsersSearchRepositoryImpl::class.java)
        bind(WebSocketRepository::class.java).to(WebSocketRepositoryImpl::class.java)

    }


}