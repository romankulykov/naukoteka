package uddug.com.naukoteka.presentation.chat_flow.chat_add_contact

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.repositories.Section
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView


@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatAddContactView : MvpView, LoadingView, InformativeView {

    fun showContacts(items: List<Section>)

}