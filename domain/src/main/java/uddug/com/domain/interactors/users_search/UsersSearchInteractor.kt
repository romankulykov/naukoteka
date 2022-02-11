package uddug.com.domain.interactors.users_search

import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.domain.SchedulersProvider
import uddug.com.domain.entities.HttpException
import uddug.com.domain.entities.ServerApiError
import uddug.com.domain.repositories.Header
import uddug.com.domain.repositories.Section
import uddug.com.domain.repositories.users_search.UsersSearchRepository

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