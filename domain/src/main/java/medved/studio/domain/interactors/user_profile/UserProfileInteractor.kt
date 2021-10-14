package medved.studio.domain.interactors.user_profile

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import medved.studio.domain.SchedulersProvider
import medved.studio.domain.entities.HttpException
import medved.studio.domain.entities.ServerApiError
import medved.studio.domain.interactors.user_profile.model.ShortInfoUi
import medved.studio.domain.repositories.user_profile.UserProfileRepository
import toothpick.InjectConstructor

@InjectConstructor
class UserProfileInteractor(
    private val userProfileRepository: UserProfileRepository,
    private val schedulers: SchedulersProvider,
) {

    fun getProfileNickname(): Single<String> {
        return userProfileRepository.getProfileNickName()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun setUser(shortInfoEntity: ShortInfoUi): Completable {
        return userProfileRepository.setUser(shortInfoEntity)
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