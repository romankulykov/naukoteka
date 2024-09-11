package uddug.com.naukoteka.utils.ui

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import uddug.com.naukoteka.GlideApp

fun ImageView.load(
    model: Any?,
    withAnimation: Boolean = true,
    requestOptions: RequestOptions? = null,
    @DrawableRes placeholder: Int? = android.R.color.transparent,
    @DrawableRes error: Int? = placeholder

) {
    GlideApp.with(this).load(model)
        .placeholder(placeholder ?: 0)
        .error(error ?: 0)
        .apply(requestOptions ?: RequestOptions())
        .apply {
            if (withAnimation) {
                transition(
                    DrawableTransitionOptions.withCrossFade(
                        DrawableCrossFadeFactory.Builder()
                            .setCrossFadeEnabled(true).build()
                    )
                )
            }
        }
        .into(this)
}

fun ImageView.loadAsBitmap(
    model: Any?,
    requestOptions: RequestOptions? = null,
    @DrawableRes placeholder: Int? = android.R.color.transparent,
    @DrawableRes error: Int? = placeholder

) {
    GlideApp.with(this).asBitmap().load(model)
        .placeholder(placeholder ?: 0)
        .error(error ?: 0)
        .apply(requestOptions ?: RequestOptions())
        .addListener(object : RequestListener<Bitmap>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                error?.let { this@loadAsBitmap.setImageResource(it) }
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                this@loadAsBitmap.setImageBitmap(resource)
                return true
            }
        })
        .into(this)
}
