package uddug.com.data.repositories.websockets

import io.reactivex.Completable
import io.reactivex.Flowable
import toothpick.InjectConstructor
import uddug.com.data.WebSocketDelegate
import uddug.com.domain.repositories.websockets.WebSocketRepository
import uddug.com.domain.utils.asCompletable

@InjectConstructor
class WebSocketRepositoryImpl(private val webSocketDelegate: WebSocketDelegate) :
    WebSocketRepository {

    override fun close(): Completable = asCompletable { webSocketDelegate.close() }

    override fun open(): Completable = asCompletable { webSocketDelegate.open() }

    override fun emit(args: Any, eventName: String): Completable {
        webSocketDelegate.emit(eventName, args)
        return Completable.complete()
    }

    override fun send(args: Any): Completable {
        webSocketDelegate.send(args)
        return Completable.complete()
    }

    override fun <T> observe(type: Class<T>, selector: String): Flowable<T> {
        return webSocketDelegate.observe(type, selector)
    }


}