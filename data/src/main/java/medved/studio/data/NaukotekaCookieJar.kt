package medved.studio.data

import medved.studio.data.cache.cookies.CookiesCache
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import toothpick.InjectConstructor

@InjectConstructor
class NaukotekaCookieJar(private val cookiesCache: CookiesCache) : CookieJar {

    private var cookies: List<Cookie>? = null

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        if (cookies != null) {
            return cookies!!
        }
        return listOf()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        this.cookies = cookies
        cookiesCache.entity = cookies.firstOrNull().toString()
    }
}