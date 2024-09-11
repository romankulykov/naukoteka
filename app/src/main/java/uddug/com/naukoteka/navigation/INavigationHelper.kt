package uddug.com.naukoteka.navigation

interface INavigationHelper : BottomNavigationClickListener {

    fun showPreviousTabIfExist(tabToDestroy: Screens.BottomNavigationTab)
    fun bottomMenuVisibility(isVisible: Boolean)
    fun bottomMenuItemSelected(tabToSelect : Screens.BottomNavigationTab, screenTag: String? = null)
}