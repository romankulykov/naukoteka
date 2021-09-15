package medved.studio.pharmix.di.providers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import medved.studio.pharmix.di.utils.CalendarTypeAdapter
import medved.studio.pharmix.di.utils.registerIntTypeAdapter
import toothpick.InjectConstructor
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

@InjectConstructor
class GsonProvider(private val calendarTypeAdapter: CalendarTypeAdapter) :
    Provider<Gson> {
    override fun get(): Gson {
        return GsonBuilder()
            .enableComplexMapKeySerialization()
            .setLenient()
            .registerTypeAdapter(Calendar::class.java, calendarTypeAdapter)
            .registerTypeAdapter(GregorianCalendar::class.java, calendarTypeAdapter)
            .create()
    }
}