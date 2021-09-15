package medved.studio.pharmix.di.modules

import com.github.terrakok.cicerone.Router
import medved.studio.pharmix.navigation.TabRouterHolder
import toothpick.config.Module

class FlowNavigationModule(router: Router) : Module() {
    init {
        bind(TabRouterHolder::class.java).toInstance(TabRouterHolder(router))
    }
}
