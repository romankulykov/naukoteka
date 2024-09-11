package uddug.com.data.cache.token

import android.content.SharedPreferences
import com.google.gson.Gson
import toothpick.InjectConstructor
import uddug.com.data.cache.base.SharedPreferencesCache
import uddug.com.data.utils.fromJson

@InjectConstructor
class UserTokenCache(private val gson: Gson, preferences: SharedPreferences) :
    SharedPreferencesCache<String>(preferences) {

    override val entityKey: String = "user_token_cache"

    override fun toJson(entity: String): String = gson.toJson(entity)

    override fun fromJson(entityJson: String): String = gson.fromJson(entityJson)


}