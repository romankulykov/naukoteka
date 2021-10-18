package uddug.com.domain.interactors.user_profile

import io.reactivex.Completable
import io.reactivex.Observable
import uddug.com.domain.SchedulersProvider
import uddug.com.domain.entities.HttpException
import uddug.com.domain.entities.ServerApiError
import uddug.com.domain.interactors.user_profile.model.ShortInfoUi
import uddug.com.domain.repositories.user_profile.UserProfileRepository
import toothpick.InjectConstructor

@InjectConstructor
class UserProfileInteractor(
    private val userProfileRepository: UserProfileRepository,
    private val schedulers: SchedulersProvider,
) {

    fun setUser(shortInfoEntity: ShortInfoUi): Completable {
        return userProfileRepository.checkNickname(shortInfoEntity.nickname)
            .flatMapCompletable { isFree ->
                if (isFree) {
                    userProfileRepository.setUser(shortInfoEntity)
                } else {
                    throw UnsupportedOperationException("Nickname is not free")
                }
            }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun checkNickname(nickname: String): Observable<Boolean> {
        return userProfileRepository.checkNickname(nickname)
            //.onErrorReturnItem(false)
            .onErrorReturn {
                if (it is HttpException && it.statusCode == ServerApiError.Unauthorized) {
                    throw it
                }
                false
            }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }


}