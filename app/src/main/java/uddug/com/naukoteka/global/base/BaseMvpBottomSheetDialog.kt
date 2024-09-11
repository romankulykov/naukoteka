package uddug.com.naukoteka.global.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import moxy.MvpDelegate
import moxy.MvpDelegateHolder
import toothpick.Scope
import toothpick.ktp.KTP
import uddug.com.naukoteka.R
import uddug.com.naukoteka.di.DI

abstract class BaseBottomSheetDialog(open val activity: FragmentActivity) :
    BottomSheetDialog(activity, R.style.CustomBottomSheetDialogTheme), MvpDelegateHolder {

    private var isStateSaved = false
    private var mvpDelegate: MvpDelegate<out BaseBottomSheetDialog>? = null

    abstract val layoutRes: Int
    abstract val contentView: ViewBinding

    protected val bottomSheetView get() = findViewById<FrameLayout>(R.id.design_bottom_sheet)

    init {
        getScope()
            .inject(this)
    }

    init {
        window?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.abbey)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMvpDelegate().onCreate(savedInstanceState)
    }

    fun getScope(): Scope {
        return KTP.openRootScope()
            .openSubScope(DI.APP_SCOPE)
            .openSubScope(DI.MAIN_ACTIVITY_SCOPE)
    }

    override fun onStart() {
        super.onStart()
        getMvpDelegate().onAttach()
    }

    override fun onStop() {
        super.onStop()
        getMvpDelegate().onDetach()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        getMvpDelegate().onDestroyView()
        if (activity.isFinishing) {
            getMvpDelegate().onDestroy()
        }
    }

    override fun dismiss() {
        super.dismiss()
    }

    fun setPeekHeight(peekHeight: Int) {
        bottomSheetView?.let { frameLayout ->
            BottomSheetBehavior.from(frameLayout).setPeekHeight(peekHeight, true)
        }
    }


    override fun getMvpDelegate(): MvpDelegate<out BaseBottomSheetDialog?> {
        if (mvpDelegate == null) {
            mvpDelegate = MvpDelegate(this)
        }

        return mvpDelegate as MvpDelegate<out BaseBottomSheetDialog?>
    }
}