package uddug.com.domain.repositories.session

import io.reactivex.Completable

interface SessionRepository {

    fun clearSessionCache(): Completable

}