package medved.studio.pharmix

import android.app.Application
import android.content.res.Configuration
import com.franmontiel.localechanger.LocaleChanger
import medved.studio.pharmix.di.DI
import medved.studio.pharmix.di.modules.AppModule
import medved.studio.pharmix.ext.data.SUPPORTED_LOCALES
import toothpick.Scope
import toothpick.ktp.KTP

class PharmixApplication : Application() {

    lateinit var scope: Scope

    override fun onCreate() {
        super.onCreate()
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