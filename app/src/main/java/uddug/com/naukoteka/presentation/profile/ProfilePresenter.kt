package uddug.com.naukoteka.presentation.profile

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter


@InjectConstructor
@InjectViewState
class ProfilePresenter(val router: AppRouter) : BasePresenterImpl<ProfileView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun exit() {
        router.exit()
    }
}