package uddug.com.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.socket.client.IO
import io.socket.client.Manager
import io.socket.client.Socket
import io.socket.engineio.client.Transport
import org.json.JSONArray
import org.json.JSONObject
import toothpick.InjectConstructor
import uddug.com.data.cache.token.UserTokenCache
import uddug.com.data.utils.fromJson
import uddug.com.domain.repositories.websockets.models.SocketReadMessageResponseDto
import uddug.com.domain.utils.logging.ILogger

@InjectConstructor
class WebSocketDelegate(
    private val gson: Gson,
    private val logger: ILogger,
    private val tokenCache: UserTokenCache,
) {

    private var eventSubject: PublishSubject<Any>? = null
    var webSocketClient: Socket? = null


    fun <T> observe(type: Class<T>, selector: String): Flowable<T> =
        (eventSubject ?: Observable.empty<String>())
            .flatMap {
                Observable.fromIterable(
                    JSONArray(it).let { jsonArray ->
                        val length = jsonArray.length()
                        val result = mutableListOf<String>()
                        for (i in 0 until length) {
                            result.add(jsonArray.getString(i))
                        }

                        result
                    }
                )
            }
            .filter { json ->
                try {
                    val readType = gson.fromJson(json, SocketReadMessageResponseDto::class.java)
                    if (readType.action != null) {
                        false
                    } else {
                        gson.fromJson(json, type) != null
                    }
                } catch (e: Throwable) {
                    false
                }
            }
            .map { json ->
                gson.fromJson(json, type)
            }
            .toFlowable(BackpressureStrategy.BUFFER)

    fun close() {
        if (webSocketClient != null) {
            eventSubject?.onComplete()
            webSocketClient?.close()
            webSocketClient = null
            eventSubject = null
        }
    }

    fun open() {
        if (webSocketClient == null) {
            eventSubject = PublishSubject.create()

            val url = "https://stage.naukotheka.ru"
            //val url = "ws://api.skylife-staging.cloudike.io/subscribe/"

            val opt = IO.Options()
            opt.path = "/api/chat/socket.io/"
            //opt.extraHeaders = mapOf("Authorization" to listOf(tokenCache.entity) )

            webSocketClient = IO.socket(url, opt)
            webSocketClient!!.io().on(Manager.EVENT_TRANSPORT) {
                val transport = it[0] as Transport;

                transport.on(Transport.EVENT_REQUEST_HEADERS) {
                    val headers = it[0] as MutableMap<String, List<String>>

                    // modify request headers
                    headers["Authorization"] = listOf(tokenCache.entity!!);
                }

                transport.on(Transport.EVENT_RESPONSE_HEADERS) {
                    val cookie = it[0] as MutableMap<String, List<String>>
                };
            }
            webSocketClient?.on(Socket.EVENT_CONNECT) {
                logger.debug("EVENT_CONNECT")
            }
            webSocketClient?.on(Socket.EVENT_DISCONNECT) {
                logger.debug("EVENT_DISCONNECT")
            }
            webSocketClient?.on(Socket.EVENT_CONNECT_ERROR) {
                logger.debug("EVENT_CONNECT_ERROR")
            }
            webSocketClient?.on("message") {
                logger.debug("message")
                eventSubject?.onNext(it)
            }
            webSocketClient?.connect()
        }
    }

    private inline fun <reified T> Gson.type(message: Any): T? {
        return try {
            fromJson<T>(message.toString())
        } catch (e: Exception) {
            null
        }
    }

    fun emit(eventName: String, args: Any) {
        val raw = gson.toJson(args)
        val json = JSONObject(raw)
        webSocketClient!!.emit(eventName, json)
    }

    fun send(args: Any) {
        webSocketClient!!.send(args)
    }

}

data class WebSocketType(@SerializedName("type") val type: String)