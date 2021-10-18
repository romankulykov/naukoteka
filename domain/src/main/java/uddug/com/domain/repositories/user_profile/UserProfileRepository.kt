package uddug.com.domain.repositories.user_profile

import io.reactivex.Completable
import io.reactivex.Observable
import uddug.com.domain.interactors.user_profile.model.ShortInfoUi

interface UserProfileRepository {
    fun setUser(shortInfoEntity: ShortInfoUi): Completable
    fun checkNickname(nickname: String): Observable<Boolean>
}