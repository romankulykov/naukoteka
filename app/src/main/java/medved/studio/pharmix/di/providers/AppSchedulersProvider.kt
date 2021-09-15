package medved.studio.pharmix.di.providers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import medved.studio.domain.SchedulersProvider

class AppSchedulersProvider : SchedulersProvider {

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    override fun io(): Scheduler = Schedulers.io()
    override fun computatution(): Scheduler = Schedulers.computation()

}