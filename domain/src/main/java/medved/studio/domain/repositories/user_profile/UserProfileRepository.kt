package medved.studio.domain.repositories.user_profile

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import medved.studio.domain.interactors.user_profile.model.ShortInfoUi

interface UserProfileRepository {
    fun setUser(shortInfoEntity: ShortInfoUi): Completable
    fun checkNickname(nickname: String): Observable<Boolean>
}