package uddug.com.naukoteka.utils.rx

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import moxy.MvpView
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.global.views.LoadingView

@SuppressLint("CheckResult")
fun <V : MvpView> Completable.subscribe(
    withProgress: Boolean,
    withRefreshProgress: Boolean,
    presenter: BasePresenterImpl<V>,
    onError: (throwable: Throwable) -> Unit,
    onComplete: OnComplete
): Disposable {

    if (withProgress) (presenter.viewState as? LoadingView)?.showLoading(true)
    if (withRefreshProgress) (presenter.viewState as? LoadingView)?.showRefreshLoading(true)
    return doOnComplete {
        if (withProgress) (presenter.viewState as? LoadingView)?.showLoading(false)
        if (withRefreshProgress) (presenter.viewState as? LoadingView)?.showRefreshLoading(false)
    }
        .doOnError {
            if (withProgress) (presenter.viewState as? LoadingView)?.showLoading(false)
            if (withRefreshProgress) (presenter.viewState as? LoadingView)?.showRefreshLoading(false)
        }
        .subscribe(onComplete, onError)

}

