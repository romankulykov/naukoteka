package uddug.com.naukoteka

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.franmontiel.localechanger.LocaleChanger
import toothpick.Scope
import toothpick.ktp.KTP
import uddug.com.naukoteka.data.SUPPORTED_LOCALES
import uddug.com.naukoteka.di.DI
import uddug.com.naukoteka.di.modules.AppModule

class NaukotekaApplication : Application() {

    companion object {
        var context: Context? = null
    }

    lateinit var scope: Scope

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initializeToothpick()
        LocaleChanger.initialize(this, SUPPORTED_LOCALES)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleChanger.onConfigurationChanged()
    }

    private fun initializeToothpick() {
        scope = KTP.openRootScope()
            .openSubScope(DI.APP_SCOPE)
            .installModules(AppModule(this))
    }

}