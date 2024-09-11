package uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.files

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.entities.FilesEntity
import uddug.com.domain.repositories.dialogs.models.SearchMedia
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView


@StateStrategyType(AddToEndSingleStrategy::class)
interface FilesView : MvpView, LoadingView, InformativeView {

    fun showFiles(files: List<SearchMedia>)

}