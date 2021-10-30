package uddug.com.naukoteka.ui.widgets

import android.content.Context
import android.os.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import uddug.com.domain.utils.logging.ILogger
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.BottomBarBinding
import uddug.com.naukoteka.di.DI
import uddug.com.naukoteka.navigation.BottomNavigationClickListener
import uddug.com.naukoteka.navigation.Screens
import uddug.com.naukoteka.utils.textColor


class BottomMenuView constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val tabsExpanded
        get() = binding.run {
            listOf(
                R.id.tv_nau_sphere,
                R.id.tv_nau_profile,
                R.id.tv_nau_chat,
                R.id.tv_my_territory,
                R.id.tv_nau_search
            )
        }

    private val Int.getById
        get() =
            when (this) {
                R.id.tv_nau_sphere -> Screens.BottomNavigationTab.NAU_SPHERE
                R.id.tv_nau_profile -> Screens.BottomNavigationTab.NAU_PROFILE
                R.id.tv_nau_chat -> Screens.BottomNavigationTab.NAU_CHAT
                R.id.tv_my_territory -> Screens.BottomNavigationTab.MY_TERRITORY
                R.id.tv_nau_search -> Screens.BottomNavigationTab.NAU_SEARCH
                else -> throw UnsupportedOperationException("Unsupported id in screens")
            }

    private var bottomNavigationClickListener: BottomNavigationClickListener? = null
    private var selectedExpandedId: Int = 0

    private val logger: ILogger by inject()

    val binding =
        BottomBarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        KTP.openRootScope()
            .openSubScope(DI.APP_SCOPE)
            .inject(this)

        View.inflate(context, R.layout.bottom_bar, this)

        binding.run {

            tvNauSphere.setOnClickListener {
                bottomNavigationClickListener?.onTabSelect(
                    Screens.BottomNavigationTab.NAU_SPHERE
                )
                selectSphere()
            }

            tvNauProfile.setOnClickListener {
                bottomNavigationClickListener?.onTabSelect(
                    Screens.BottomNavigationTab.NAU_PROFILE
                )
                selectNauProfile()
            }

            tvNauChat.setOnClickListener {
                bottomNavigationClickListener?.onTabSelect(
                    Screens.BottomNavigationTab.NAU_CHAT
                )
                selectChat()
            }

            tvMyTerritory.setOnClickListener {
                bottomNavigationClickListener?.onTabSelect(
                    Screens.BottomNavigationTab.MY_TERRITORY
                )
                selectMyTerritory()
            }

            tvNauSearch.setOnClickListener {
                bottomNavigationClickListener?.onTabSelect(
                    Screens.BottomNavigationTab.NAU_SEARCH
                )
                selectNauSearch()
            }

        }
    }

    private fun select(startState: Int, endState: Int): Boolean {
        if (startState != endState)
            binding.run {

                selectedExpandedId = endState
            }
        return startState == endState
    }

    fun select(navBarItem: Screens.BottomNavigationTab) {
        when (navBarItem) {
            Screens.BottomNavigationTab.NAU_SPHERE -> selectSphere()
            Screens.BottomNavigationTab.NAU_PROFILE -> selectNauProfile()
            Screens.BottomNavigationTab.NAU_CHAT -> selectChat()
            Screens.BottomNavigationTab.MY_TERRITORY -> selectMyTerritory()
            Screens.BottomNavigationTab.NAU_SEARCH -> selectNauSearch()
        }
    }

    fun setSelectedValueIndex(expandedId: Int) {
        logger.debug("bottom menu: selectedRoot id =  ${expandedId}")
        tabsExpanded.onEach { currentSelectedId ->
            logger.debug("bottom menu: current id =  ${currentSelectedId}")
            if (expandedId == currentSelectedId) {
                logger.debug("bottom menu: select from restore")
                Handler(Looper.getMainLooper()).postDelayed({
                    select(expandedId.getById)
                }, 500L)
            }
        }
    }

    fun startClickListeners(bottomNavigationClickListener: BottomNavigationClickListener) {
        this.bottomNavigationClickListener = bottomNavigationClickListener
    }

    private fun TextView.change(color: Int) {
        textColor(color)
        val topIndex = 1
        val drawableTop = compoundDrawables[topIndex]
        val color = ContextCompat.getColor(context, color)
        drawableTop.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                color,
                BlendModeCompat.SRC_IN
            )
    }

    private fun unselectAll() {
        val unselectedColor = R.color.text_inactive
        tabsExpanded.forEach { findViewById<TextView>(it).change(unselectedColor) }
    }

    fun selectSphere() {
        binding.tvNauSphere.run {
            unselectAll()
            change(R.color.white)
        }
    }

    fun selectNauProfile() {
        binding.tvNauProfile.run {
            unselectAll()
            change(R.color.white)
        }
    }

    fun selectChat() {
        binding.tvNauChat.run {
            unselectAll()
            change(R.color.white)
        }
    }

    fun selectMyTerritory() {
        binding.tvMyTerritory.run {
            unselectAll()
            change(R.color.white)
        }
    }

    fun selectNauSearch() {
        binding.tvNauSearch.run {
            unselectAll()
            change(R.color.white)
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val savedState = super.onSaveInstanceState()?.let { SavedState(it) }
        savedState?.selectedExpandedId = selectedExpandedId
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        logger.debug("bottom menu: onRestoreInstanceState")
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            setSelectedValueIndex(state.selectedExpandedId)
            logger.debug("bottom menu: setSelectedValueIndex")
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    internal class SavedState : View.BaseSavedState {

        var selectedExpandedId: Int = 0

        constructor(source: Parcel) : super(source) {
            selectedExpandedId = source.readInt()
        }

        constructor(superState: Parcelable) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(selectedExpandedId)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {

                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }


}


