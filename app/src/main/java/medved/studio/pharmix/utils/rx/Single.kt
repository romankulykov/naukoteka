package medved.studio.pharmix.utils.rx

import android.annotation.SuppressLint
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.global.views.LoadingView
import moxy.MvpView

typealias OnSuccess<R> = (response: R) -> Unit
typealias OnError = (throwable: Throwable) -> Unit


@SuppressLint("CheckResult")
fun <T, V : MvpView> Single<T>.subscribe(
    withProgress: Boolean,
    presenter: BasePresenterImpl<V>,
    onError: (throwable: Throwable) -> Unit,
    onSuccess: OnSuccess<T>
): Disposable {

    if (withProgress) {
        (presenter.viewState as? LoadingView)?.showLoading(true)
    }
    return doOnSuccess { if (withProgress) (presenter.viewState as? LoadingView)?.showLoading(false) }
        .doOnError { if (withProgress) (presenter.viewState as? LoadingView)?.showLoading(false) }
        .subscribe(onSuccess, onError)

}

