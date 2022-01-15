package uddug.com.domain.repositories.websockets

import io.reactivex.Completable
import org.json.JSONObject

interface WebSocketRepository {
    fun close(): Completable
    fun open(): Completable
    fun emit(eventName: String,  args : JSONObject): Completable
    fun send( args : JSONObject): Completable
}