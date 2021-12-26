package uddug.com.domain.repositories

sealed class Section {
    abstract fun type(): Int

    abstract var sectionPosition: Int

    var positionInSection = 0
    var maxPosition = 0
    abstract fun sectionName(): String?

    companion object {
        const val ITEM = 1
        const val HEADER = 0
    }
}

data class Header(val character: String, override var sectionPosition: Int) : Section() {
    override fun type(): Int = HEADER
    override fun sectionName(): String = character
}