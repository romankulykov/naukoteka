package uddug.com.naukoteka.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.viewpager.widget.ViewPager

class WrapContentHeightViewPager constructor(
    context: Context, attrs: AttributeSet?,
) : ViewPager(context, attrs) {

    private var mScrollView: NestedScrollView? = null

    fun initScrollView(scrollView: NestedScrollView?) {
        mScrollView = scrollView
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            val h = child.measuredHeight
            if (h > height) height = h
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    var downX = 0
    var downY = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val dragthreshold = 30
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.rawX.toInt()
                downY = event.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val distanceX = Math.abs(event.rawX.toInt() - downX)
                val distanceY = Math.abs(event.rawY.toInt() - downY)
                if (distanceY > distanceX && distanceY > dragthreshold) {
                    parent.requestDisallowInterceptTouchEvent(false)
                    mScrollView?.parent?.requestDisallowInterceptTouchEvent(true)
                } else if (distanceX > distanceY && distanceX > dragthreshold) {
                    parent.requestDisallowInterceptTouchEvent(true)
                    mScrollView?.parent?.requestDisallowInterceptTouchEvent(false)
                }
            }
            MotionEvent.ACTION_UP -> {
                mScrollView?.parent?.requestDisallowInterceptTouchEvent(false)
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return false
    }
}