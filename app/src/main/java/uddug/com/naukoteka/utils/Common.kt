package uddug.com.naukoteka.utils

import android.content.Context
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.io.PrintWriter
import java.io.StringWriter

fun Throwable.stackTraceToString(): String {
    val sw = StringWriter()
    val pw = PrintWriter(sw)
    printStackTrace(pw)
    pw.flush()
    return sw.toString()
}

inline fun <A, B> ifAllNotNull(a: A?, b: B?, block: (A, B) -> Unit) {
    if (a != null && b != null) block(a, b)
}

inline fun <reified T : Any> Any?.letIfIs(run: (T) -> Unit) {
    if (this is T) run(this)
}

inline fun runIf(predicate: Boolean, run: () -> Unit) {
    if (predicate) run()
}

inline fun <T> tryOr(default: T, get: () -> T): T = try {
    get()
} catch (e: Exception) {
    e.printStackTrace()
    default
}

inline fun <T> tryOrNull(get: () -> T) = tryOr(null, get)

inline fun tryTo(run: () -> Unit): Unit = try {
    run()
} catch (e: Exception) {
    e.printStackTrace()
}

inline fun Context.withPermissions(vararg permissions: String, crossinline onGranted: () -> Unit) =
    TedPermission
        .with(this)
        .setPermissionListener(permissionListener(onGranted))
        .setPermissions(*permissions)
        .check()

inline fun permissionListener(crossinline onGranted: () -> Unit) = object : PermissionListener {
    override fun onPermissionGranted() = onGranted()
    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) = Unit
}