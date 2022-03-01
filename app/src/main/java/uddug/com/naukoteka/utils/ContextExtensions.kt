package uddug.com.naukoteka.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun Context.getColorCompat(colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}

fun Context.useStyledAttributes(
    attrs: AttributeSet,
    styleableRes: IntArray,
    action: TypedArray.() -> Unit
) {
    obtainStyledAttributes(attrs, styleableRes).run {
        action()
        recycle()
    }
}

fun TextView.textColor(@ColorRes colorId: Int) = setTextColor(context.color(colorId))

@SuppressLint("UseCompatLoadingForColorStateLists")
fun Context.color(@ColorRes resId: Int) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) getColor(resId) else resources.getColor(
        resId
    )

fun Context.getDrawableCompat(drawableId: Int): Drawable {
    return ContextCompat.getDrawable(this, drawableId)!!
}

fun Context.showKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
