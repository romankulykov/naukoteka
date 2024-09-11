package uddug.com.naukoteka.utils.ui

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*


inline fun EditText.afterTextChangedDelay(
    crossinline run: (text: Editable) -> Unit
) = addTextChangedListener(object : TextWatcher {
    private var timer = Timer()
    private val DELAY: Long = 700
    override fun afterTextChanged(text: Editable) {
        if (text.isEmpty()) {
            timer.cancel()
            run(text)
        } else {
            timer.cancel()
            timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    Handler(Looper.getMainLooper()).post {
                        run(text)
                    }
                }
            }, DELAY)
        }
    }

    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}
})