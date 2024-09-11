package uddug.com.naukoteka.presentation.chat_flow.search_in_chapter.files

import moxy.InjectViewState
import toothpick.InjectConstructor
import uddug.com.domain.entities.MediaCategory
import uddug.com.domain.interactors.dialogs.DialogsInteractor
import uddug.com.domain.repositories.dialogs.models.AttachmentChatPreview
import uddug.com.domain.repositories.dialogs.models.ContentType
import uddug.com.domain.repositories.dialogs.models.SearchMedia
import uddug.com.naukoteka.di.modules.SearchInChatModule
import uddug.com.naukoteka.global.base.BasePresenterImpl
import uddug.com.naukoteka.navigation.AppRouter

@InjectConstructor
@InjectViewState
class FilesPresenter(
    val router: AppRouter,
    private val dialogsInteractor: DialogsInteractor,
    private val searchInChat: SearchInChatModule.SearchInChatParams,
) : BasePresenterImpl<FilesView>() {

    private fun listOfFiles(): ArrayList<SearchMedia> {
        val files: ArrayList<SearchMedia> = arrayListOf()
        for (i in 0..14) {
            files.add(
                SearchMedia(
                    0,
                    0,
                    0,
                    MediaCategory.FILES.category,
                    "",
                    AttachmentChatPreview(
                        "",
                        "",
                        MediaCategory.FILES.category,
                        "Домашняя работа.xlsx",
                        ContentType.OTHER
                    )
                )
            )
        }
        return files
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (searchInChat.dialogId != null) {
            dialogsInteractor.searchMediaContent(searchInChat.dialogId, MediaCategory.FILES)
                .await {
                    viewState.showFiles(it)
                }
        } else {
            viewState.showFiles(listOfFiles())
        }
    }

    fun exit() {
        router.exit()
    }
}