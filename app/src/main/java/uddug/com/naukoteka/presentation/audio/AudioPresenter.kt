package uddug.com.naukoteka.presentation.audio

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.entities.AudioEntity
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter

@InjectConstructor
@InjectViewState
class AudioPresenter(val router: AppRouter) : BasePresenterImpl<AudioView>() {

    private val listOfAudio = listOf(
        AudioEntity("Мария Тарасова", "0:12", "14 июля 2021 в 13:34"),
        AudioEntity("Мария Тарасова", "0:12", "14 июля 2021 в 13:34"),
        AudioEntity("Мария Тарасова", "0:12", "14 июля 2021 в 13:34"),
        AudioEntity("Мария Тарасова", "0:12", "14 июля 2021 в 13:34"),
        AudioEntity("Мария Тарасова", "0:12", "14 июля 2021 в 13:34"),
        AudioEntity("Мария Тарасова", "0:12", "14 июля 2021 в 13:34"),
        AudioEntity("Мария Тарасова", "0:12", "14 июля 2021 в 13:34"),
        AudioEntity("Мария Тарасова", "0:12", "14 июля 2021 в 13:34"),
        AudioEntity("Мария Тарасова", "0:12", "14 июля 2021 в 13:34"),
        AudioEntity("Мария Тарасова", "0:12", "14 июля 2021 в 13:34"),
    )

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showAudio(listOfAudio)
    }

    fun exit() {
        router.exit()
    }
}