package uddug.com.naukoteka.utils.rx

typealias OnComplete = () -> Unit
typealias OnNext<R> = (response: R) -> Unit
typealias OnSuccess<R> = (response: R) -> Unit
typealias OnError = (throwable: Throwable) -> Unit