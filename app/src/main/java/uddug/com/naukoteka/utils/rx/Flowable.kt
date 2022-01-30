package uddug.com.naukoteka.utils.rx

import android.annotation.SuppressLint
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import moxy.MvpView
import uddug.com.naukoteka.global.LoaderType
import uddug.com.naukoteka.global.base.BasePresenterImpl

@SuppressLint("CheckResult")
fun <T, V : MvpView> Flowable<T>.subscribe(
    loaderType: LoaderType,
    presenter: BasePresenterImpl<V>,
    onError: (throwable: Throwable) -> Unit,
    onComplete: () -> Unit,
    onNext: OnNext<T>
): Disposable {

    val withProgress = loaderType.withProgress

    val hideLoading = presenter.viewState.applyLoading(loaderType)

    return doOnNext { if (withProgress) hideLoading() }
        .doOnError { if (withProgress) hideLoading() }
        .subscribe(onNext, onError, onComplete)

}

