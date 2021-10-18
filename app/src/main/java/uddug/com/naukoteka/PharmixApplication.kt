package uddug.com.naukoteka

import android.app.Application
import uddug.com.naukoteka.di.DI
import uddug.com.naukoteka.di.modules.AppModule
import toothpick.Scope
import toothpick.ktp.KTP

class PharmixApplication : Application() {

    lateinit var scope: Scope

    override fun onCreate() {
        super.onCreate()
        initializeToothpick()
    }

    private fun initializeToothpick() {
        scope = KTP.openRootScope()
            .openSubScope(DI.APP_SCOPE)
            .installModules(AppModule(this))
    }

}