package uddug.com.domain.interactors.users_search

import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.domain.SchedulersProvider
import uddug.com.domain.entities.HttpException
import uddug.com.domain.entities.ServerApiError
import uddug.com.domain.repositories.Header
import uddug.com.domain.repositories.Section
import uddug.com.domain.repositories.dialogs.models.ChatPreview
import uddug.com.domain.repositories.dialogs.models.DialogType
import uddug.com.domain.repositories.users_search.UsersSearchRepository
import uddug.com.domain.repositories.users_search.models.UserStatus

@InjectConstructor
class UsersSearchInteractor(
    private val usersSearchRepository: UsersSearchRepository,
    private val schedulers: SchedulersProvider
) {

    fun usersSearch(query: String): Single<List<Section>> {
        return if (query.isEmpty()) {
            Single.just(emptyList())
        } else {
            usersSearchRepository.searchUsers(query)
                .onErrorReturn {
                    if ((it as HttpException).statusCode == ServerApiError.NotFound) {
                        emptyList()
                    } else throw it
                }
                .map { it.sortByFirstLetter() }
        }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun getUsersChatStatuses(chats: List<ChatPreview>): Single<List<String>> {
        val usersToFind =
            chats.filter { it.dialogType == DialogType.PERSONAL && it.interlocutor != null }
                .map { it.interlocutor!!.userId }.distinct()

        if (usersToFind.isEmpty()) {
            return Single.just(emptyList())
        }
        return getUsersStatus(usersToFind)
            .map { it.filter { it.status.isOnline }.map { it.userId } }
    }

    fun getUsersStatus(usersUUIDs: List<String>): Single<List<UserStatus>> {
        return usersSearchRepository.usersStatus(usersUUIDs)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    private fun <T : Section> List<T>.sortByFirstLetter(): List<Section> {
        val sections = arrayListOf<Section>()
        val firstLettersAndSortedUsers =
            filter { it.getName().isNotEmpty() }.sortedBy { it.getName() }
                .groupBy { it.getName().first() }

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

}