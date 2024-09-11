package uddug.com.domain.broadcasting.session

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import toothpick.InjectConstructor
import uddug.com.domain.utils.logging.ILogger


@InjectConstructor
class SessionExpirationBus constructor(
    private val logger: ILogger
) {

    // TODO refactor to publish subject

    companion object {
        @JvmStatic
        private val relay: PublishRelay<Boolean> = PublishRelay.create()
    }

    internal fun getObservable(): Observable<Boolean> = relay

    internal fun emit() {
        logger.info("Emitting session expiration..")
        relay.accept(true)
    }
}