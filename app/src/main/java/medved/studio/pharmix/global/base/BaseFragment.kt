package medved.studio.pharmix.global.base

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.viewbinding.ViewBinding
import medved.studio.pharmix.di.DI
import medved.studio.pharmix.global.views.InformativeView
import medved.studio.pharmix.global.views.LoadingView
import medved.studio.pharmix.ui.custom.square_toast.SquareToast
import medved.studio.pharmix.ui.custom.square_toast.ToastInfo
import moxy.MvpAppCompatFragment
import toothpick.Scope
import toothpick.ktp.KTP

abstract class BaseFragment(layoutResId: Int) : MvpAppCompatFragment(layoutResId), LoadingView,
    InformativeView {

    abstract val contentView: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getScope()
            .inject(this)
    }

    open fun getScope(): Scope {
        return KTP.openRootScope()
            .openSubScope(DI.APP_SCOPE)
            .openSubScope(DI.MAIN_ACTIVITY_SCOPE)
    }

    open fun onFragmentResume() {
        childFragmentManager.fragments
            .filterIsInstance(BaseFragment::class.java)
            .forEach { it.onFragmentResume() }
    }

    override fun showLoading(show: Boolean) {
        (activity as? LoadingView)?.showLoading(show)
    }

    override fun showRefreshLoading(show: Boolean) {
    }

    override fun showInfoMessage(message: Int?) {
        (activity as? InformativeView)?.showInfoMessage(message)
    }

    override fun showInfoMessage(message: String?) {
        (activity as? InformativeView)?.showInfoMessage(message)
    }

    override fun showActionMessage(message: String?, action: String, invoker: () -> Unit) {
        (activity as? InformativeView)?.showActionMessage(message, action, invoker)
    }

    override fun showActionMessage(message: Int?, action: Int, invoker: () -> Unit) {
        (activity as? InformativeView)?.showActionMessage(message, action, invoker)
    }

    override fun showMessage(data: ToastInfo) {
        (activity as? InformativeView)?.showMessage(data)
    }

    fun setBackpressedDispatcherActionListener(actionListener: (() -> Unit)) {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    actionListener.invoke()
                }
            })
    }

}