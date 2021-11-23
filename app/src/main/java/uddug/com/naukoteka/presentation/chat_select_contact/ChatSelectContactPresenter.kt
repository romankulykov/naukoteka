package uddug.com.naukoteka.presentation.chat_select_contact

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.repositories.contacts.models.ChatContact
import uddug.com.domain.repositories.contacts.models.Header
import uddug.com.domain.repositories.contacts.models.Section
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter
import uddug.com.naukoteka.navigation.Screens

@InjectConstructor
@InjectViewState
class ChatSelectContactPresenter(val router: AppRouter) :
    BasePresenterImpl<ChatSelectContactView>() {

    val listOfChatContact = mutableListOf(
        ChatContact("Михаил Маркин", "@michael"),
        ChatContact("Александр Ивановский", "@alex"),
        ChatContact("Vasiliy Chernenko", "@vaska"),
        ChatContact("Andrew Zakharov", "@andruha"),
        ChatContact("Elena Babaeva", "@elena"),
        ChatContact("Александра Землянко", "@alex"),
        ChatContact("Andy", "@andy"),
        ChatContact("Алексей Демьяненко", "@leha"),
        ChatContact("Vadim Gubierna", "@vaduha"),
        ChatContact("Михаил Пахомов", "@mickle"),
        ChatContact("Дмитрий Розуванов", "@dimon"),
        ChatContact("Татьяна Бугеря", "@bugeria"),
        ChatContact("Олег Николаев", "@oleg"),
        ChatContact("Юлия Козлова", "@julia")
    )

    private val selectedChatList: MutableList<ChatContact> = mutableListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showContacts(listOfChatContact.sortByFirstLetter())
    }

    fun toCreateGroup() {
        router.navigateTo(Screens.CreateGroup())
    }

    fun removeSelectedContacts(chatContact: ChatContact) {
        if (selectedChatList.contains(chatContact)) {
            selectedChatList.remove(chatContact)
            viewState.showSelectedContacts(selectedChatList)
        }
    }

    private fun filterChatContacts(query: String): List<Section> {
        return listOfChatContact.filter { it.name.contains(query, ignoreCase = true) }
            .sortByFirstLetter()
            ?: emptyList()
    }

    private fun List<ChatContact>.sortByFirstLetter(): List<Section> {
        val sections = arrayListOf<Section>()
        val firstLettersAndSortedUsers =
            filter { it.name.isNotEmpty() }.sortedBy { it.name }
                .groupBy { it.name.first() }

        firstLettersAndSortedUsers.toList()
            .forEachIndexed { characterIndex, (character, sortedChatContacts) ->
                sections.add(Header(character.toString(), characterIndex))
                sortedChatContacts.forEachIndexed { index, chatContact ->
                    sections.add(chatContact.apply {
                        sectionPosition = characterIndex
                        positionInSection = index
                        maxPosition = sortedChatContacts.size - 1
                    })
                }
            }
        return sections.toList()
    }

    fun onQueryFilter(query: String) {
        filterChatContacts(query).also { viewState.showContacts(it) }
    }

    fun onContactClicked(chatContact: ChatContact) {
        addSelectedContacts(chatContact)
    }

    private fun addSelectedContacts(chatContact: ChatContact) {
        if (!selectedChatList.contains(chatContact)) {
            selectedChatList.add(chatContact)
            viewState.showSelectedContacts(selectedChatList, true)
        } else {
            selectedChatList.remove(chatContact)
            viewState.showSelectedContacts(selectedChatList)
        }

    }

    fun exit() {
        router.exit()
    }
}