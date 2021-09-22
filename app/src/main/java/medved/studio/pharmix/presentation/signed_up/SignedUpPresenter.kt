package medved.studio.pharmix.presentation.signed_up

import android.content.Context
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import moxy.InjectViewState
import toothpick.InjectConstructor

@InjectConstructor
@InjectViewState
class SignedUpPresenter (
    private val context: Context,
    val router: AppRouter
    ) : BasePresenterImpl<SignedUpView>(){

    override fun attachView(view: SignedUpView?) {
        super.attachView(view)
    }

}