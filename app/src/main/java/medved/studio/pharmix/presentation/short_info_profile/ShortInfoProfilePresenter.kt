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
    private var defaultNickname: String? = null

    private val inputNicknameSubject = BehaviorSubject.create<String>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        startListenNicknameChanges()
        //getUserProfileNickname()
    }

    private fun getUserProfileNickname() {
        userProfileInteractor.getProfileNickname()
            .await {
                defaultNickname = it.replace("id","")
                viewState.showDefaultNickname(defaultNickname!!)
            }
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

    fun isValidFields(surname: String, name: String, patronymic: String, withoutPatronymic : Boolean, link: String) {
        viewState.showButtonState(
            fieldsValidator.isNotEmpty(surname) &&
                    fieldsValidator.isNotEmpty(name) &&
                    (withoutPatronymic || fieldsValidator.isNotEmpty(patronymic)) &&
                    fieldsValidator.isNotEmpty(link) &&
                    isAvailableNickname
        )
    }

    fun checkFreeNickname(nickname: String) {
        if (nickname == defaultNickname) {
            isAvailableNickname = true
            viewState.showNicknameAvailable(isAvailableNickname)
        } else {
            inputNicknameSubject.onNext(nickname)
        }
    }

    fun fillProfile(shortInfoUi: ShortInfoUi) {
        userProfileInteractor.setUser(shortInfoUi)
            .await {
                viewState.showInfoMessage("Данные заполнены успешно")
                router.newRootScreen(Screens.Login())
            }
    }
}