package uddug.com.naukoteka.presentation.chat_select_contact

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.repositories.contacts.models.ChatContact
import uddug.com.domain.repositories.contacts.models.Section
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView


@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatSelectContactView : MvpView, LoadingView, InformativeView {

    fun showContacts(items: List<Section>)

    fun showSelectedContacts(items: List<ChatContact>, scrollToEnd: Boolean = false)

}