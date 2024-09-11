package uddug.com.data.cache

import toothpick.InjectConstructor
import uddug.com.data.cache.token.UserTokenCache
import uddug.com.data.cache.user_uuid.UserUUIDCache
import uddug.com.domain.utils.logging.ILogger


@InjectConstructor
class CacheComposite constructor(
    private val userUUID: UserUUIDCache,
    private val tokenCache: UserTokenCache,
    private val logger: ILogger
) {

    fun clearAll() {
        userUUID.clear()
        tokenCache.clear()
        logger.info("Cache is cleared")
    }
}