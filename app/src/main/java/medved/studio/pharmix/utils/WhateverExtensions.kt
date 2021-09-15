package medved.studio.pharmix.utils

fun <T> T.getOrNullCondition(condition: Boolean): T? {
    return if (condition) this else null
}