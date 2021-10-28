package uddug.com.naukoteka.presentation.chat_detail

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatDetailView : MvpView, LoadingView, InformativeView {

    fun showDialogSearchByConversation()
    fun showDialogInterviewMaterials()
    fun showDisableNotifications()
    fun showClearTheHistory()
    fun showDialogAddParticipant()
    fun showOptionsDialog()

}