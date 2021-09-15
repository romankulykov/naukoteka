package medved.studio.pharmix.di.utils

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import medved.studio.domain.entities.shouldBeReported
import medved.studio.domain.utils.logging.ILogger
import medved.studio.pharmix.BuildConfig
import toothpick.InjectConstructor
import javax.inject.Inject

@InjectConstructor
class Logger : ILogger {

    private val callerTag: String
        get() {
            val callerStackTraceElement = Thread.currentThread().stackTrace[5]

            val className = callerStackTraceElement.className
            val simpleClassName = className.split('.').last()
            val methodName = callerStackTraceElement.methodName

            return "Log $simpleClassName.$methodName"
        }

    override fun debug(message: String) {
        Log.d(callerTag, message)
    }

    @SuppressLint("CheckResult")
    override fun report(message: String, reportInDebug: Boolean) {
        if (BuildConfig.DEBUG && !reportInDebug) {
            return
        }

        // TODO make call on something to notificate me about error immediately
        /*Single.fromCallable {
            OkHttpClient().newCall(
                Request.Builder()
                .url(slackWebHookUrl)
                .post(
                    RequestBody.create(
                    MediaType.get("application/json"),
                    gson.toJson(SlackReport(listOf(SlackAttachment(message))))
                ))
                .build())
                .execute()
        }
            .subscribeOn(schedulers.io())
            .subscribe({}, {})*/
    }

    override fun info(message: String) {
        FirebaseCrashlytics.getInstance().log(message)
    }

    override fun warn(message: String, throwable: Throwable?) {
        if (throwable != null) {
            throwable.printStackTraceIfRequired()
            logException(Log.WARN, message, throwable)
        } else {
            FirebaseCrashlytics.getInstance().log(message)
        }
    }

    override fun error(message: String, throwable: Throwable) {
        throwable.printStackTraceIfRequired()
        logException(Log.ERROR, message, throwable)
    }

    override fun setUserIdentifier(identifier: String) {
        FirebaseCrashlytics.getInstance().setUserId(identifier)
    }

    override fun <T> setState(key: String, value: T) {
        when (value) {
            is String -> FirebaseCrashlytics.getInstance().setCustomKey(key, value)
            is Boolean -> FirebaseCrashlytics.getInstance().setCustomKey(key, value)
            is Int -> FirebaseCrashlytics.getInstance().setCustomKey(key, value)
            is Float -> FirebaseCrashlytics.getInstance().setCustomKey(key, value)
            is Double -> FirebaseCrashlytics.getInstance().setCustomKey(key, value)
            else -> FirebaseCrashlytics.getInstance().setCustomKey(key, value.toString())
        }

    }

    private fun Throwable.printStackTraceIfRequired() {
        if (BuildConfig.DEBUG) {
            printStackTrace()
        }
    }

    private fun logException(logLevel: Int, message: String, throwable: Throwable) {
        if (throwable.shouldBeReported()) {
            throwable.message?.let { FirebaseCrashlytics.getInstance().log(it) }
        }
    }

}