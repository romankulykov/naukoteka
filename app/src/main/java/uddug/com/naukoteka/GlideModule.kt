package uddug.com.naukoteka

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import uddug.com.naukoteka.di.DI
import java.io.InputStream

@GlideModule
class GlideModule : AppGlideModule() {

    private val okHttpClient: OkHttpClient by inject()

    init {
        getScope()
            .inject(this)
    }

    fun getScope(): Scope {
        return KTP.openRootScope()
            .openSubScope(DI.APP_SCOPE)
    }


    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        glide.registry.replace(
            GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(
                okHttpClient
            )
        )
    }

    // Disable manifest parsing to avoid adding similar modules twice.
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

}