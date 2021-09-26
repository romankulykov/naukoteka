package medved.studio.pharmix.presentation.signed_up

import android.content.Context
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class SignedUpFinishedPresenter (
    private val context: Context,
    val router: AppRouter
    ) : BasePresenterImpl<SignedUpView>(){

    override fun attachView(view: SignedUpView?) {
        super.attachView(view)
    }

    fun fillProfile(){
        router.navigateTo(Screens.ShortInfoProfile())
    }

    fun skip() {
        router.newRootScreen(Screens.Login())
    }

}