package uddug.com.domain.repositories

class ChatContact(private val name: String, val nickname: String) : Section() {
    override fun sectionName(): String = name.substring(0, 1)
    override var sectionPosition: Int = Integer.MAX_VALUE
    override fun getName(): String {
        return name
    }
}