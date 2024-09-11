package uddug.com.domain.repositories.websockets

import io.reactivex.Completable
import io.reactivex.Flowable

interface WebSocketRepository {
    fun close(): Completable
    fun open(): Completable
    fun emit(args: Any, eventName: String = "message"): Completable
    fun send(args: Any): Completable
    fun <T> observe(type: Class<T>, selector: String): Flowable<T>
}