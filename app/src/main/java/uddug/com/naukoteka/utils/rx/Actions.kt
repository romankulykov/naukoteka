package uddug.com.naukoteka.utils.rx

import moxy.MvpView
import uddug.com.naukoteka.global.LoaderType
import uddug.com.naukoteka.global.ProgressLoading
import uddug.com.naukoteka.global.ShimmerLoading
import uddug.com.naukoteka.global.SwipeRefreshLoading
import uddug.com.naukoteka.global.views.LoadingView
import uddug.com.naukoteka.global.views.ShimmerView
import uddug.com.naukoteka.global.views.SwipeRefreshView

typealias OnComplete = () -> Unit
typealias OnNext<R> = (response: R) -> Unit
typealias OnSuccess<R> = (response: R) -> Unit
typealias OnError = (throwable: Throwable) -> Unit


fun MvpView.applyLoading(loaderType: LoaderType): () -> Unit? {
    if (loaderType.withProgress) {
        when (loaderType) {
            ProgressLoading -> (this as? LoadingView)?.showLoading(true)
            ShimmerLoading -> (this as? ShimmerView)?.showShimmerLoading(true)
            SwipeRefreshLoading -> (this as? SwipeRefreshView)?.showRefreshLoading(true)
        }
    }

    val hideLoading = {
        when (loaderType) {
            ProgressLoading -> (this as? LoadingView)?.showLoading(false)
            ShimmerLoading -> (this as? ShimmerView)?.showShimmerLoading(false)
            SwipeRefreshLoading -> (this as? SwipeRefreshView)?.showRefreshLoading(false)
        }
    }
    return hideLoading
}