package medved.studio.pharmix.ui.widgets

import android.content.Context
import android.os.*
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import medved.studio.data.validator.FieldsValidator
import medved.studio.pharmix.R
import medved.studio.pharmix.databinding.ViewCustomizedTextInputBinding
import medved.studio.pharmix.di.DI
import medved.studio.pharmix.utils.ui.px
import medved.studio.pharmix.utils.useStyledAttributes
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class CustomizedTextInput(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val validator: FieldsValidator by inject()

    enum class TypeInput(val number: Int) {
        TYPE_INPUT_PASSWORD(0),
        TYPE_INPUT_EMAIL(1),
        TYPE_INPUT_TEXT(2)
    }

    private var isOpenedEye = false

    private val binding =
        ViewCustomizedTextInputBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        KTP.openRootScope()
            .openSubScope(DI.APP_SCOPE)
            .inject(this)

        View.inflate(context, R.layout.view_customized_text_input, this@CustomizedTextInput)
        context.useStyledAttributes(attrs, R.styleable.CustomizedTextInput) {
            R.styleable.CustomizedTextInput_hint.let {
                if (hasValue(it)) {
                    val resId = getResourceId(it, -1)
                    if (resId != -1) {
                        val text = getString(it) ?: ""
                        binding.tilEmail.hint = text
                    } else {
                        val text = getString(it) ?: ""
                        binding.tilEmail.hint = text
                    }
                }
            }
            R.styleable.CustomizedTextInput_typeInput.let { type ->
                if (hasValue(type)) {
                    val value = getInt(R.styleable.CustomizedTextInput_typeInput, 0)
                    val typeInput = TypeInput.values().first { it.number == value }
                    val textInputType = when (typeInput) {
                        TypeInput.TYPE_INPUT_PASSWORD -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        TypeInput.TYPE_INPUT_EMAIL -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                        TypeInput.TYPE_INPUT_TEXT -> InputType.TYPE_CLASS_TEXT
                    }
                    binding.metField.inputType = textInputType
                    binding.metField.doAfterTextChanged {
                        if (typeInput == TypeInput.TYPE_INPUT_EMAIL) {
                            binding.ivCheck.isVisible = validator.isValidEmail(it.toString())
                        }
                        if (typeInput == TypeInput.TYPE_INPUT_PASSWORD) {
                            binding.ivEye.isVisible = it.toString().isNotEmpty()
                        }

                    }
                }
            }
        }
        binding.ivEye.setOnClickListener { toggleEye() }

    }

    fun showError(isError: Boolean) {
        binding.clEditText.updateLayoutParams<MarginLayoutParams> {
            if (isError) {
                setMargins(0, 0, 0, 3.px)
            } else {
                setMargins(0, 0, 0, 0)
            }
        }
    }

    fun doAfterTextChange(invoke: (String) -> Unit) =
        binding.metField.doAfterTextChanged { invoke(it.toString()) }

    fun text() = binding.metField.text.toString()

    private fun toggleEye() {
        isOpenedEye = !isOpenedEye
        binding.run {
            ivEye.setImageResource(if (isOpenedEye) R.drawable.ic_eye else R.drawable.ic_eye_closed)
            metField.inputType =
                if (isOpenedEye) InputType.TYPE_CLASS_TEXT else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            metField.setSelection(metField.text.toString().length)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                metField.setTextAppearance(R.style.TextViewStyle_Usual_EditText)
            }
        }
    }


    private fun setQueryAfterRestore(query: String) {
        Handler(Looper.getMainLooper())
            .postDelayed({
                binding.metField.setText(query)
            }, 100L)
    }


    override fun onSaveInstanceState(): Parcelable? {
        val savedState = super.onSaveInstanceState()?.let { SavedState(it) }
        savedState?.query = binding.metField.text.toString()
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            setQueryAfterRestore(state.query)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    internal class SavedState : BaseSavedState {

        var query: String = ""

        constructor(source: Parcel) : super(source) {
            query = source.readString() ?: ""
        }

        constructor(superState: Parcelable) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(query)
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