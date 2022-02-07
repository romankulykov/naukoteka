package uddug.com.domain.interactors.account

import io.reactivex.Completable
import io.reactivex.Observable
import medved.studio.domain.repositories.session.SessionRepository
import uddug.com.domain.SchedulersProvider
import uddug.com.domain.broadcasting.session.SessionExpirationBus
import javax.inject.Inject

class SessionInteractor @Inject constructor(
    private val sessionExpirationBus: SessionExpirationBus,
    private val sessionRepo: SessionRepository,
    private val schedulers: SchedulersProvider
) {

    fun onSessionExpired() = sessionExpirationBus.emit()

    fun getSessionExpirationObservable(): Observable<Boolean> {
        return sessionExpirationBus.getObservable()
                .subscribeOn(schedulers.io())
                .flatMap {
                    clearSessionCache().andThen(Observable.just(it))
                }
                .observeOn(schedulers.ui())
    }

    fun clearSessionCache(): Completable {
        return sessionRepo.clearSessionCache()
    }
}
