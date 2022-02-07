package uddug.com.naukoteka.presentation.tabs_holder

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.interactors.account.SessionInteractor
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens

@InjectViewState
@InjectConstructor
class TabsHolderPresenter(
    private val router: AppRouter,
    private val sessionInteractor: SessionInteractor
) : BasePresenterImpl<TabsHolderView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        sessionInteractor.getSessionExpirationObservable()
            .subscribe {
                router.replaceScreen(Screens.Login())
                disposeAll()
            }
            .connect()
    }

}