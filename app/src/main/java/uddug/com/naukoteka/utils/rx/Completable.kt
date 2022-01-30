package uddug.com.naukoteka.utils.rx

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import moxy.MvpView
import uddug.com.naukoteka.global.LoaderType
import uddug.com.naukoteka.global.ProgressLoading
import uddug.com.naukoteka.global.ShimmerLoading
import uddug.com.naukoteka.global.SwipeRefreshLoading
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.global.views.LoadingView
import uddug.com.naukoteka.global.views.ShimmerView
import uddug.com.naukoteka.global.views.SwipeRefreshView

@SuppressLint("CheckResult")
fun <V : MvpView> Completable.subscribe(
    loaderType: LoaderType,
    presenter: BasePresenterImpl<V>,
    onError: (throwable: Throwable) -> Unit,
    onComplete: OnComplete
): Disposable {

    val viewState = presenter.viewState
    val withProgress = loaderType.withProgress
    if (withProgress) {
        when (loaderType) {
            ProgressLoading -> (viewState as? LoadingView)?.showLoading(true)
            ShimmerLoading -> (viewState as? ShimmerView)?.showShimmerLoading(true)
            SwipeRefreshLoading -> (viewState as? SwipeRefreshView)?.showRefreshLoading(true)
        }
    }
    val hideLoading = {
        when (loaderType) {
            ProgressLoading -> (viewState as? LoadingView)?.showLoading(false)
            ShimmerLoading -> (viewState as? ShimmerView)?.showShimmerLoading(false)
            SwipeRefreshLoading -> (viewState as? SwipeRefreshView)?.showRefreshLoading(false)
        }
    }
    return doOnComplete { if (withProgress) hideLoading() }
        .doOnError { if (withProgress) hideLoading() }
        .subscribe(onComplete, onError)

}

