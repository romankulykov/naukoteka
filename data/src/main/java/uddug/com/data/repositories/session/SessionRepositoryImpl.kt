package uddug.com.data.repositories.session

import io.reactivex.Completable
import uddug.com.domain.repositories.session.SessionRepository
import toothpick.InjectConstructor
import uddug.com.data.cache.CacheComposite
import uddug.com.domain.utils.logging.ILogger

@InjectConstructor
class SessionRepositoryImpl constructor(
    private val cacheComposite: CacheComposite,
    private val logger: ILogger
) : SessionRepository {

    override fun clearSessionCache(): Completable {
        return Completable.fromAction {
            logger.info("Clearing session cache..")
            logger.setUserIdentifier("")
            cacheComposite.clearAll()
        }
    }
}