package uddug.com.naukoteka.utils.rx

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import moxy.MvpView
import uddug.com.naukoteka.global.LoaderType
import uddug.com.naukoteka.global.base.BasePresenterImpl

@SuppressLint("CheckResult")
fun <V : MvpView> Completable.subscribe(
    loaderType: LoaderType,
    presenter: BasePresenterImpl<V>,
    onError: (throwable: Throwable) -> Unit,
    onComplete: OnComplete
): Disposable {

    val withProgress = loaderType.withProgress

    val hideLoading = presenter.viewState.applyLoading(loaderType)

    return doOnComplete { if (withProgress) hideLoading() }
        .doOnError { if (withProgress) hideLoading() }
        .subscribe(onComplete, onError)

}

