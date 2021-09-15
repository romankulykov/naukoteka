package medved.studio.domain

import io.reactivex.Scheduler


interface SchedulersProvider {
    fun ui() : Scheduler
    fun io() : Scheduler
    fun computatution() : Scheduler
}