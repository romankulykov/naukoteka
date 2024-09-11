package uddug.com.naukoteka.navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import toothpick.InjectConstructor

@InjectConstructor
class TabCiceroneHolder(private val navigationHelper: INavigationHelper) {

    private val containers = LinkedHashMap<String, Cicerone<AppRouter>>()

    fun getCicerone(containerTag: String): Cicerone<AppRouter> =
        containers.getOrPut(containerTag) {
            create(AppRouter())
        }

    fun isTherePreviousTab(): Boolean {
        return containers.entries.size - 1 > 0
    }

    fun showLastTab(currentTab: Screens.BottomNavigationTab) {
        navigationHelper.showPreviousTabIfExist(currentTab)
        removeContainer(currentTab.tabName)
    }

    fun removeContainer(tabKey: String) {
        containers.remove(tabKey)
    }

}