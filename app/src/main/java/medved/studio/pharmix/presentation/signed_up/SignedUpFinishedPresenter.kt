package medved.studio.pharmix.presentation.signed_up

import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class SignedUpFinishedPresenter(
    val router: AppRouter
) : BasePresenterImpl<SignedUpView>() {


    fun fillProfile() {
        router.navigateTo(Screens.ShortInfoProfile())
    }

    fun skip() {
        router.newRootScreen(Screens.Login())
    }

}