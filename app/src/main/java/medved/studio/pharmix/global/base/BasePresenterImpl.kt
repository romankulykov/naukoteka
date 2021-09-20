package medved.studio.pharmix.global.base

import android.content.Context
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import medved.studio.domain.entities.*
import medved.studio.pharmix.R
import medved.studio.pharmix.di.DI
import medved.studio.pharmix.global.views.InformativeView
import medved.studio.pharmix.global.views.LoadingView
import medved.studio.pharmix.ui.custom.square_toast.SquareToast
import medved.studio.pharmix.ui.custom.square_toast.ToastInfo
import medved.studio.pharmix.utils.isOffline
import medved.studio.pharmix.utils.rx.OnComplete
import medved.studio.pharmix.utils.rx.OnError
import medved.studio.pharmix.utils.rx.OnSuccess
import medved.studio.pharmix.utils.rx.subscribe
import moxy.MvpPresenter
import moxy.MvpView
import okhttp3.ResponseBody
import retrofit2.HttpException
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import java.net.SocketTimeoutException

abstract class BasePresenterImpl<V : MvpView> : MvpPresenter<V>() {


    private val context: Context by inject()
    private val gson: Gson by inject()

    init {
        getScope()
            .inject(this)
    }

    fun getScope(): Scope {
        return KTP.openRootScope()
            .openSubScope(DI.APP_SCOPE)
            .openSubScope(DI.MAIN_ACTIVITY_SCOPE)
    }

    private val compositeDisposable = CompositeDisposable()


    fun Disposable.connect() {
        compositeDisposable.add(this)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    protected fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        (viewState as? LoadingView)?.showLoading(false)
        viewState?.onError(throwable)
    }

    protected fun V.onError(throwable: Throwable) = when {
        throwable is SocketTimeoutException -> showErrorMessage(R.string.errors_timeout_error)
        throwable is HttpException -> showErrorMessage(throwable.readMessage())
        throwable is EmailNotFreeException -> showErrorMessage(R.string.errors_email_is_busy)
        context.isOffline() -> showErrorMessage(R.string.errors_network_error)
        else -> showErrorMessage(throwable.message)
    }

    private fun HttpException.readMessage(): String? {
        return response()?.errorBody()?.readMessage() ?: message()
    }

    private fun ResponseBody.readMessage(): String? {
        return try {
            val apiError = gson.fromJson(string(), ApiErrorWrapper::class.java)
            apiError?.apiErrorDetail?.message
        } catch (e: Exception) {
            string()
        }
    }

    protected fun showMessage(textResId: Int) {
        showMessage(context.getString(textResId))
    }

    protected fun showMessage(text: String?) {
        if (!text.isNullOrEmpty()) {
            (viewState as? InformativeView)?.showInfoMessage(text)
        } else {
            showMessage(R.string.errors_something_went_wrong)
        }
    }

    protected fun showErrorMessage(textResId: Int) {
        showErrorMessage(context.getString(textResId))
    }

    protected fun showErrorMessage(text: String?) {
        if (!text.isNullOrEmpty()) {
            (viewState as? InformativeView)?.showMessage(
                ToastInfo(
                    text = text,
                    type = SquareToast.Type.ERROR
                )
            )
        } else {

        }
    }


    protected fun <R> Single<R>.await(
        withProgress: Boolean = true,
        onError: OnError = ::onError,
        onSuccess: OnSuccess<R>
    ) = subscribe(withProgress, this@BasePresenterImpl, onError) { response ->
        onSuccess(response)
    }.also { it.connect() }

    protected fun <R> Observable<R>.await(
        withProgress: Boolean = true,
        onError: OnError = ::onError,
        onComplete: OnComplete = {},
        onSuccess: OnSuccess<R>
    ) = subscribe(withProgress, this@BasePresenterImpl, onError, onComplete) { response ->
        onSuccess(response)
    }.also { it.connect() }

    protected fun Completable.await(
        withProgress: Boolean = true,
        onError: OnError = ::onError,
        onComplete: OnComplete
    ) = subscribe(withProgress, this@BasePresenterImpl, onError) {
        onComplete()
    }.also { it.connect() }


}
