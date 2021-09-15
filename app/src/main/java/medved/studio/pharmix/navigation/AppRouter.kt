package medved.studio.pharmix.navigation

import com.github.terrakok.cicerone.*
import java.util.*

class AppRouter : Router() {

    fun newScreenChainFrom(
        screenKeyBeforeChain: Screen,
        screenKey: Screen,
        clearContainer: Boolean = false
    ) {
        executeCommands(
            BackTo(screenKeyBeforeChain),
            Forward(screenKey, clearContainer)
        )
    }

    fun exitWithMessage() {
        exit()
    }


}