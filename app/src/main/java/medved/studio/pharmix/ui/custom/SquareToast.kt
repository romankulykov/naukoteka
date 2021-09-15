package medved.studio.pharmix.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import medved.studio.pharmix.R
import medved.studio.pharmix.ui.custom.swipedismissdialog.Params
import medved.studio.pharmix.ui.custom.swipedismissdialog.SwipeDismissDialog
import medved.studio.pharmix.utils.ui.load

@SuppressLint("ViewConstructor")
class SquareToast private constructor(context: Context, params: Params) :
    SwipeDismissDialog(context, params) {
    private var dismissRunnable: Runnable? = null
    override fun show(): SwipeDismissDialog {
        throw RuntimeException("Use show(String text) instead")
    }

    fun show(text: String?) {
        val tvToast = findViewById<TextView>(R.id.tv_message)
        findViewById<View>(R.id.tv_action).isVisible = false
        findViewById<View>(R.id.iv_icon_left).isVisible = false
        tvToast.text = text
        super.showToast()
        dismissRunnable = Runnable { this.dismiss() }
        postDelayed(dismissRunnable, 5000)
    }

    fun show(text: String?, action: String, invoker: () -> Unit) {
        val tvToast = findViewById<TextView>(R.id.tv_message)
        tvToast.text = text
        val tvAction = findViewById<TextView>(R.id.tv_action)
        tvAction.run { setText(action); isVisible = true }
        findViewById<View>(R.id.iv_icon_left).isVisible = false
        tvAction.setOnClickListener {
            invoker()
            dismissRunnable?.run()
        }
        super.showToast()
        dismissRunnable = Runnable { this.dismiss() }
        postDelayed(dismissRunnable, 5000)
    }

    fun showIconable(text: String?, drawableResId: Int) {
        val tvToast = findViewById<TextView>(R.id.tv_message)
        tvToast.text = text
        findViewById<View>(R.id.tv_action).isVisible = false
        findViewById<ImageView>(R.id.iv_icon_left).run {
            isVisible = true
            setImageResource(drawableResId)
        }

        super.showToast()
        dismissRunnable = Runnable { this.dismiss() }
        postDelayed(dismissRunnable, 5000)
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(dismissRunnable)
        super.onDetachedFromWindow()
    }

    companion object {
        fun getInstance(context: Context): SquareToast {
            val params = Params()
            params.layoutRes = R.layout.layout_square_toast
            params.overlayColor = android.R.color.transparent
            params.horizontalSwipeEnabled = false
            params.verticalSwipeEnabled = false
            params.isRotationEnabled = false
            params.dismissOnClick = true
            params.dismissOnCancel = true
            params.customLayoutGravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            return SquareToast(context, params)
        }
    }
}