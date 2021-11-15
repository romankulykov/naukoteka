package uddug.com.naukoteka.di.modules

import com.github.terrakok.cicerone.Router
import toothpick.config.Module
import uddug.com.naukoteka.navigation.TabRouterHolder

class FlowNavigationModule(router: Router) : Module() {
    init {
        bind(TabRouterHolder::class.java).toInstance(TabRouterHolder(router))
    }
}
