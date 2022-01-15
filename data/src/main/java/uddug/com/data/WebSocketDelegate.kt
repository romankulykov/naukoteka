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
import io.socket.emitter.Emitter
import io.socket.engineio.client.Transport
import org.json.JSONArray
import org.json.JSONObject
import toothpick.InjectConstructor
import uddug.com.data.cache.token.UserTokenCache
import uddug.com.domain.utils.logging.ILogger
import java.util.*

@InjectConstructor
class WebSocketDelegate(
    private val gson: Gson,
    private val logger: ILogger,
    private val tokenCache: UserTokenCache,
) {

    private var eventSubject: PublishSubject<String>? = null
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
                if (gson.fromJson(json, WebSocketType::class.java).type == selector) {
                    try {
                        gson.fromJson(json, type) != null
                    } catch (e: Throwable) {
                        false
                    }
                } else {
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
            eventSubject = PublishSubject.create<String>()

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
            webSocketClient?.on(Socket.EVENT_CONNECT, object : Emitter.Listener {
                override fun call(vararg args: Any?) {
                    logger.debug("EVENT_CONNECT")
                }
            })
            webSocketClient?.on(Socket.EVENT_DISCONNECT, object : Emitter.Listener {
                override fun call(vararg args: Any?) {
                    logger.debug("EVENT_DISCONNECT")
                }
            })
            webSocketClient?.on(Socket.EVENT_CONNECT_ERROR, object : Emitter.Listener {
                override fun call(vararg args: Any?) {
                    logger.debug("EVENT_CONNECT_ERROR")
                }
            })
            webSocketClient?.on("message", object : Emitter.Listener {
                override fun call(vararg args: Any?) {
                    logger.debug("message")
                }
            })
            webSocketClient?.connect()
            val isConnected = webSocketClient?.connected()
            logger.debug("isConnected = $isConnected")
            webSocketClient?.send()
            webSocketClient?.emit("foo_event")
        }
    }

    fun emit(eventName: String, args: JSONObject) {
        webSocketClient!!.emit(eventName, args)
    }

    fun send( args: JSONObject) {
        webSocketClient!!.send(args)
    }

}

data class WebSocketType(@SerializedName("type") val type: String)