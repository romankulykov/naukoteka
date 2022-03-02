package uddug.com.naukoteka.utils.ui

import android.app.Activity
import android.content.res.Resources
import android.graphics.Insets
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.Size
import android.view.WindowInsets


val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Activity.getScreenHeight(): Int {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val metrics = windowManager.currentWindowMetrics
        // Gets all excluding insets
        // Gets all excluding insets
        val windowInsets = metrics.windowInsets
        val insets: Insets = windowInsets.getInsetsIgnoringVisibility(
            WindowInsets.Type.navigationBars()
                    or WindowInsets.Type.displayCutout()
        )

        val insetsWidth: Int = insets.right + insets.left
        val insetsHeight: Int = insets.top + insets.bottom

        // Legacy size that Display#getSize reports

        // Legacy size that Display#getSize reports
        val bounds: Rect = metrics.bounds
        val legacySize = Size(
            bounds.width() - insetsWidth,
            bounds.height() - insetsHeight
        )
        return legacySize.height
    } else {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width: Int = size.x
        val height: Int = size.y
        return height
    }

}