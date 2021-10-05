package medved.studio.data.cache.cookies

import android.content.SharedPreferences
import com.google.gson.Gson
import medved.studio.data.cache.base.SharedPreferencesCache
import medved.studio.data.utils.fromJson
import toothpick.InjectConstructor

@InjectConstructor
class CookiesCache(private val gson: Gson, preferences: SharedPreferences) :
    SharedPreferencesCache<String>(preferences) {

    override val entityKey: String = "cookieCache"

    override fun toJson(entity: String): String = gson.toJson(entity)

    override fun fromJson(entityJson: String): String = gson.fromJson(entityJson)
}