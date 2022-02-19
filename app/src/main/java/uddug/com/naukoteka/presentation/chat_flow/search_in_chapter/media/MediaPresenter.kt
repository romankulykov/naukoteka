package uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.media

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.entities.MediaEntity
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter


@InjectConstructor
@InjectViewState
class MediaPresenter(val router: AppRouter) : BasePresenterImpl<MediaView>() {

    private fun listOfMedia(): ArrayList<MediaEntity> {
        val entities = arrayListOf<MediaEntity>()
        for (i in 0..30) {
            entities.addAll(
                listOf(
                    MediaEntity("https://avatanplus.com/files/resources/mid/5c00f2aac915316763b3eb48.png"),
                    MediaEntity("https://seoportal.net/images/articles/png.png"),
                    MediaEntity("https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png")
                )
            )
        }
        return entities
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showMedia(listOfMedia())
    }

    fun exit() {
        router.exit()
    }
}