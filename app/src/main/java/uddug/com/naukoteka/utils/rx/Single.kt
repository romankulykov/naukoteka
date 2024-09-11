package uddug.com.naukoteka.utils.rx

import android.annotation.SuppressLint
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import moxy.MvpView
import uddug.com.naukoteka.global.LoaderType
import uddug.com.naukoteka.global.base.BasePresenterImpl


@SuppressLint("CheckResult")
fun <T, V : MvpView> Single<T>.subscribe(
    loaderType: LoaderType,
    presenter: BasePresenterImpl<V>,
    onError: (throwable: Throwable) -> Unit,
    onSuccess: OnSuccess<T>
): Disposable {

    val withProgress = loaderType.withProgress

    val hideLoading = presenter.viewState.applyLoading(loaderType)

    return doOnSuccess { if (withProgress) hideLoading() }
        .doOnError { if (withProgress) hideLoading() }
        .subscribe(onSuccess, onError)

}

