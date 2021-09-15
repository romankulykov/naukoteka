package medved.studio.pharmix.ui.custom.swipedismissdialog;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import medved.studio.pharmix.R;

public class Params {
    public View view = null;
    @LayoutRes
    public int layoutRes = 0;
    @FloatRange(from = 0, to = 1.0)
    public float flingVelocity = 0.1f;
    @ColorInt
    public int overlayColor = Color.parseColor("#80444444");
    @Nullable
    public OnSwipeDismissListener swipeDismissListener;
    @Nullable
    public View.OnClickListener clickListener;
    @Nullable
    public OnCancelListener cancelListener;
    @FloatRange(from = 0.0, to = 35.0)
    public float horizontalOscillation = 35.0f;
    public int defaultLayoutGravity = Gravity.CENTER;
    public int customLayoutGravity = Gravity.CENTER;
    public int dialogAnimations = R.style.FadeInFadeOutAnimation;
    public boolean dismissOnCancel = true;
    public boolean isRotationEnabled = true;
    public boolean verticalSwipeEnabled = true;
    public boolean horizontalSwipeEnabled = true;
    public boolean dismissOnClick = false;
}
