package medved.studio.pharmix.presentation.short_info_profile

import io.reactivex.subjects.BehaviorSubject
import medved.studio.data.validator.FieldsValidator
import medved.studio.domain.interactors.user_profile.UserProfileInteractor
import medved.studio.domain.interactors.user_profile.model.ShortInfoUi
import medved.studio.pharmix.global.base.BasePresenterImpl
import medved.studio.pharmix.navigation.AppRouter
import medved.studio.pharmix.navigation.Screens
import moxy.InjectViewState
import toothpick.InjectConstructor
import java.util.concurrent.TimeUnit

@InjectConstructor
@InjectViewState
class ShortInfoProfilePresenter(
    private val fieldsValidator: FieldsValidator,
    val router: AppRouter,
    private val userProfileInteractor: UserProfileInteractor
) : BasePresenterImpl<ShortInfoProfileView>() {

    private var isAvailableNickname = false

    private val inputNicknameSubject = BehaviorSubject.create<String>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        startListenNicknameChanges()
    }

    private fun startListenNicknameChanges() {
        inputNicknameSubject.debounce(500L, TimeUnit.MILLISECONDS)
            .switchMap { nickname -> userProfileInteractor.checkNickname(nickname) }
            .subscribe({ nickNameAvailable ->
                isAvailableNickname = nickNameAvailable
                viewState.showNicknameAvailable(nickNameAvailable)
            }, this::onError)
            .connect()
    }

    fun isValidFields(surname: String, name: String, patronymic: String, link: String) {
        viewState.showButtonState(
            fieldsValidator.isNotEmpty(surname) &&
                    fieldsValidator.isNotEmpty(name) &&
                    fieldsValidator.isNotEmpty(patronymic) &&
                    fieldsValidator.isNotEmpty(link) &&
                    isAvailableNickname
        )
    }

    fun nextStep() {
        router.newRootScreen(Screens.Login())
    }

    fun checkFreeNickname(nickname: String) {
        inputNicknameSubject.onNext(nickname)
    }

    fun fillProfile(shortInfoUi: ShortInfoUi) {
        userProfileInteractor.setUser(shortInfoUi)
            .await {
                viewState.showInfoMessage("Success")
            }
    }
}