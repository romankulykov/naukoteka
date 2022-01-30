package uddug.com.naukoteka.global

sealed class LoaderType(var withProgress: Boolean = true)

object ProgressLoading : LoaderType()
object SwipeRefreshLoading : LoaderType()
object ShimmerLoading : LoaderType()
