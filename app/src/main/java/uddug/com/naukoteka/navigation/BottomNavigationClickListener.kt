package uddug.com.naukoteka.navigation

interface BottomNavigationClickListener {
    fun onTabSelect(navBarItem: Screens.BottomNavigationTab, newRoot: Boolean = false)
}