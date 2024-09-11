package uddug.com.naukoteka.di.modules

import android.content.Context
import androidx.fragment.app.FragmentActivity
import toothpick.config.Module
import uddug.com.naukoteka.navigation.INavigationHelper
import uddug.com.naukoteka.navigation.NavigationHelper
import uddug.com.naukoteka.navigation.TabCiceroneHolder

class ActivityModule(activity: FragmentActivity) : Module() {

    init {
        bind(Context::class.java).toInstance(activity)
        bind(INavigationHelper::class.java).toInstance(NavigationHelper(activity))
        bind(TabCiceroneHolder::class.java).singleton()
    }
}