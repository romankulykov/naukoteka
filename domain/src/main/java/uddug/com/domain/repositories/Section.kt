package uddug.com.domain.repositories

abstract class Section {

    open fun type(): SectionType = SectionType.Item
    open var sectionPosition: Int = Integer.MAX_VALUE

    var positionInSection = 0
    var maxPosition = 0
    abstract fun getName(): String
    abstract fun sectionName(): String?

}

sealed class SectionType(val type: Int) {
    object Item : SectionType(1)
    object Header : SectionType(0)
}

data class Header(val character: String, override var sectionPosition: Int) : Section() {
    override fun type(): SectionType = SectionType.Header
    override fun sectionName(): String = character
    override fun getName(): String = character
}