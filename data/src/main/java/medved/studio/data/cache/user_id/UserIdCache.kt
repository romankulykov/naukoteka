package medved.studio.data.cache.user_id

import android.content.SharedPreferences
import com.google.gson.Gson
import medved.studio.data.cache.base.SharedPreferencesCache
import medved.studio.data.utils.fromJson
import toothpick.InjectConstructor

@InjectConstructor
class UserIdCache(private val gson: Gson, preferences: SharedPreferences) :
    SharedPreferencesCache<String>(preferences) {

    override val entityKey: String = "user_id"

    override fun toJson(entity: String): String = gson.toJson(entity)

    override fun fromJson(entityJson: String): String = gson.fromJson(entityJson)

}