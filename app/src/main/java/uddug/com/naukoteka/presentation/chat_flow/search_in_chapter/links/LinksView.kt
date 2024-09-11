package uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.links

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.entities.LinksEntity
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView


@StateStrategyType(AddToEndSingleStrategy::class)
interface LinksView : MvpView, LoadingView, InformativeView {

    fun showLinks(links: List<LinksEntity>)

}