package uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.audio

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import uddug.com.domain.entities.AudioEntity
import uddug.com.naukoteka.global.views.InformativeView
import uddug.com.naukoteka.global.views.LoadingView


@StateStrategyType(AddToEndSingleStrategy::class)
interface AudioView : MvpView, LoadingView, InformativeView {

    fun showAudio(audio: List<AudioEntity>)

}