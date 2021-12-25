package uddug.com.data

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import toothpick.InjectConstructor
import uddug.com.data.cache.cookies.CookiesCache
import uddug.com.data.cache.token.UserTokenCache

@InjectConstructor
class NaukotekaCookieJar(
    private val cookiesCache: CookiesCache,
    private val userTokenCache: UserTokenCache
) : CookieJar {

    private var cookies: List<Cookie>? = null

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        if (cookies != null) {
            return cookies?.filter { it.value().isNotEmpty() }!!
        }
        return listOf()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        this.cookies = cookies
        cookiesCache.entity = cookies.find { it.value().isNotEmpty() }?.value().toString()
        cookies.find { it.name() == "_nkts" }?.let {
            userTokenCache.entity = it.value()
        }
    }
}