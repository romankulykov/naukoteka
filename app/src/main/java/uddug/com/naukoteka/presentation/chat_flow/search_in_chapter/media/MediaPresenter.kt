package uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.media

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.entities.MediaCategory
import uddug.com.domain.interactors.dialogs.DialogsInteractor
import uddug.com.domain.repositories.dialogs.models.AttachmentChatPreview
import uddug.com.domain.repositories.dialogs.models.SearchMedia
import uddug.com.naukoteka.di.modules.SearchInChatModule
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter


@InjectConstructor
@InjectViewState
class MediaPresenter(
    val router: AppRouter,
    private val dialogsInteractor: DialogsInteractor,
    private val searchInChat: SearchInChatModule.SearchInChatParams,
) : BasePresenterImpl<MediaView>() {

    private val pageLimit: Int = 10
    private var loadMore = false

    private fun listOfMedia(): ArrayList<SearchMedia> {
        val entities = arrayListOf<SearchMedia>()
        for (i in 0..30) {
            entities.addAll(
                listOf(
                    SearchMedia(
                        0,
                        0,
                        0,
                        0,
                        "",
                        AttachmentChatPreview(
                            "",
                            "/s/Messages/9703/file_default/f01980c7-6ab9-4f83-a37e-cb0610fcf835.jpg",
                        )
                    ),
                    SearchMedia(
                        0,
                        0,
                        0,
                        0,
                        "",
                        AttachmentChatPreview(
                            "",
                            "/s/Messages/9630/file_default/9659005a-ae2d-4541-b296-f043feea4101.jpg"
                        )
                    ),
                    SearchMedia(
                        0,
                        0,
                        0,
                        0,
                        "",
                        AttachmentChatPreview(
                            "",
                            "/s/Messages/9703/file_default/f01980c7-6ab9-4f83-a37e-cb0610fcf835.jpg",
                        )
                    )
                )
            )
        }
        return entities
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        obtainData()
    }

    fun obtainData(
        withProgress: Boolean = true, lastMessageId: Int? = null,
    ) {
        if (searchInChat.dialogId != null) {
            dialogsInteractor.searchMediaContent(
                searchInChat.dialogId,
                MediaCategory.IMAGE_VIDEO,
                lastMessageId,
                pageLimit
            )
                .await(withProgress) {
                    loadMore = it.size == pageLimit
                    viewState.showMedia(it, lastMessageId == null, loadMore)
                }
        } else {
            viewState.showMedia(listOfMedia(), true, false)
        }
    }

    fun exit() {
        router.exit()
    }
}