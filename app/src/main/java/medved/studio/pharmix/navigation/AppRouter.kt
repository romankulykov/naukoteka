package medved.studio.pharmix.navigation

import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen

class AppRouter : Router() {

    fun newScreenChainFrom(
        screenKeyBeforeChain: Screen,
        screenKey: Screen
    ) {
        executeCommands(
            BackTo(screenKeyBeforeChain),
            Forward(screenKey)
        )
    }

    fun exitWithMessage() {
        exit()
    }


}