package medved.studio.pharmix.global.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import medved.studio.pharmix.di.DI
import medved.studio.pharmix.di.modules.ActivityModule
import moxy.MvpAppCompatActivity
import toothpick.Scope
import toothpick.ktp.KTP

abstract class BaseActivity : MvpAppCompatActivity() {

    protected abstract val contentView: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val scope = getScope()
        scope
                .installModules(ActivityModule(this))
                .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(contentView.root)
    }

    open fun getScope(): Scope {
        return KTP.openRootScope()
            .openSubScope(DI.APP_SCOPE)
            .openSubScope(DI.MAIN_ACTIVITY_SCOPE)
    }

    override fun onDestroy() {
        super.onDestroy()
        KTP.closeScope(DI.MAIN_ACTIVITY_SCOPE)
    }

}