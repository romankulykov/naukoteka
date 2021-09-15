package medved.studio.pharmix.utils.rx

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.global.views.LoadingView
import moxy.MvpView

typealias OnNext<R> = (response: R) -> Unit

@SuppressLint("CheckResult")
fun <T, V : MvpView> Observable<T>.subscribe(
    withProgress: Boolean,
    presenter: BasePresenterImpl<V>,
    onError: (throwable: Throwable) -> Unit,
    onComplete: () -> Unit,
    onNext: OnNext<T>
): Disposable {

    if (withProgress) {
        (presenter.viewState as? LoadingView)?.showLoading(true)
    }
    return doOnNext { if (withProgress) (presenter.viewState as? LoadingView)?.showLoading(false) }
        .doOnError { if (withProgress) (presenter.viewState as? LoadingView)?.showLoading(false) }
        .subscribe(onNext, onError, onComplete)

}

