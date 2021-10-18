package uddug.com.data.repositories.user_profile

import io.reactivex.Completable
import io.reactivex.Observable
import uddug.com.data.cache.user_uuid.UserUUIDCache
import uddug.com.data.services.UserProfileApiService
import uddug.com.data.services.models.request.user_profile.NickNameCheckRequestDto
import uddug.com.domain.interactors.user_profile.model.ShortInfoUi
import uddug.com.domain.repositories.user_profile.UserProfileRepository
import toothpick.InjectConstructor

@InjectConstructor
class UserProfileRepositoryImpl(
    private val profileApiService: UserProfileApiService,
    private val userProfileMapper: UserProfileMapper,
    private val userUUIDCache: UserUUIDCache
) : UserProfileRepository {


    override fun setUser(shortInfoEntity: ShortInfoUi): Completable {
        return profileApiService.setUser(
            userProfileRequestDto = userProfileMapper.mapDomainToDto(shortInfoEntity)
        )
            .flatMapCompletable {
                userUUIDCache.entity = it
                Completable.complete()
            }
    }

    override fun checkNickname(nickname: String): Observable<Boolean> {
        return profileApiService
            .checkNickName(NickNameCheckRequestDto(nickname = "id$nickname"))
            .flatMap { Observable.just(it.nicknameIsFree) }
    }


}