package uddug.com.naukoteka.utils.ui

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import uddug.com.naukoteka.R
import uddug.com.naukoteka.ui.adapters.long_press_menu.LongPressMenuAdapter


fun Activity.showPopupLongPress(
    adapter: LongPressMenuAdapter,
    anchor: View,
    realTapOnScreenCoordinate: Float,
    heightPossibleToDisplayPopup: Int
) {
    val child =
        LayoutInflater.from(this).inflate(R.layout.popup_long_press_menu, null)
    val popupWindow = PopupWindow(this)
    with(popupWindow) {
        contentView = child
        setBackgroundDrawable(null)
        isFocusable = true
        isOutsideTouchable = true
        with(child) {
            findViewById<RecyclerView>(R.id.rv_popup_long_press_menu).adapter = adapter
        }

        if (realTapOnScreenCoordinate > heightPossibleToDisplayPopup) {
            val diff = realTapOnScreenCoordinate - heightPossibleToDisplayPopup
            showAsDropDown(anchor, 0, -diff.toInt())
        } else showAsDropDown(anchor, 0, 0)

        applyDim(0.6f)
        setOnDismissListener { clearDim() }
    }
}

fun Fragment.applyDim(dimAmount: Float) = requireActivity().applyDim(dimAmount)

fun Fragment.clearDim() = requireActivity().clearDim()

fun Activity.applyDim(dimAmount: Float) {
    val parent = window.decorView.rootView as ViewGroup
    val dim: Drawable = ColorDrawable(Color.BLACK)
    dim.setBounds(0, 0, parent.width, parent.height)
    dim.alpha = (255 * dimAmount).toInt()
    val overlay = parent.overlay
    overlay.add(dim)
}

fun Activity.clearDim() {
    val parent = window.decorView.rootView as ViewGroup
    val overlay = parent.overlay
    overlay.clear()
}