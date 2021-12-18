package uddug.com.naukoteka.presentation.chat_flow.files

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.entities.FilesEntity
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter

@InjectConstructor
@InjectViewState
class FilesPresenter(val router: AppRouter) : BasePresenterImpl<FilesView>() {

    private val listOfFiles = listOf(
        FilesEntity("Домашняя работа.xlsx", "14 июля 2021 в 13:34", "2.2 МБ"),
        FilesEntity("Домашняя работа.xlsx", "14 июля 2021 в 13:34", "2.2 МБ"),
        FilesEntity("Домашняя работа.xlsx", "14 июля 2021 в 13:34", "2.2 МБ"),
        FilesEntity("Домашняя работа.xlsx", "14 июля 2021 в 13:34", "2.2 МБ"),
        FilesEntity("Домашняя работа.xlsx", "14 июля 2021 в 13:34", "2.2 МБ"),
        FilesEntity("Домашняя работа.xlsx", "14 июля 2021 в 13:34", "2.2 МБ"),
        FilesEntity("Домашняя работа.xlsx", "14 июля 2021 в 13:34", "2.2 МБ"),
        FilesEntity("Домашняя работа.xlsx", "14 июля 2021 в 13:34", "2.2 МБ"),
        FilesEntity("Домашняя работа.xlsx", "14 июля 2021 в 13:34", "2.2 МБ"),
        FilesEntity("Домашняя работа.xlsx", "14 июля 2021 в 13:34", "2.2 МБ"),
    )

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showFiles(listOfFiles)
    }

    fun exit() {
        router.exit()
    }
}