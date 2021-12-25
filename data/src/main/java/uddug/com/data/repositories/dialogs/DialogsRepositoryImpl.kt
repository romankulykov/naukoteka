package uddug.com.data.repositories.dialogs

import io.reactivex.Single
import toothpick.InjectConstructor
import uddug.com.data.services.DialogsApiService
import uddug.com.domain.repositories.dialogs.DialogsRepository
import uddug.com.domain.repositories.dialogs.models.ChatsPreview

@InjectConstructor
class DialogsRepositoryImpl(
    private val dialogsApiService: DialogsApiService,
    private val dialogsRepositoryMapper: DialogsRepositoryMapper,
) : DialogsRepository {

    override fun getChatsPreview(): Single<ChatsPreview> {
        return dialogsApiService.dialogs()
            .map { dialogsRepositoryMapper.mapChatsPreviewToDomain(it) }
    }

}