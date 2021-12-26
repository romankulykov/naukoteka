package uddug.com.domain.interactors.users_search

import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.domain.SchedulersProvider
import uddug.com.domain.repositories.dialogs.models.UserChatPreview
import uddug.com.domain.repositories.users_search.UsersSearchRepository

@InjectConstructor
class UsersSearchInteractor(
    private val usersSearchRepository: UsersSearchRepository,
    private val schedulers: SchedulersProvider
) {

    fun usersSearch(query: String): Single<List<UserChatPreview>> {
        return usersSearchRepository.searchUsers(query)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

}