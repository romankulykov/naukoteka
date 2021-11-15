package uddug.com.domain.repositories.contacts.models

class ChatContact(val name: String, val nickname: String) : Section() {
    override fun type(): Int = Section.ITEM
    override fun sectionName(): String = name.substring(0, 1)
    override var sectionPosition: Int = Integer.MAX_VALUE
}