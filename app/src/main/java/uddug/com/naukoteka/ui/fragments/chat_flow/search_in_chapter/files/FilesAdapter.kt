package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.files

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import uddug.com.domain.entities.FilesEntity
import uddug.com.domain.repositories.dialogs.models.SearchMedia
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemFilesBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder
import uddug.com.naukoteka.utils.ui.hourMinuteFormat
import java.util.*

class FilesAdapter :
    BaseAdapter<SearchMedia, FilesAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): FilesAdapter.ViewHolder =
        ViewHolder(R.layout.list_item_files, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<SearchMedia>(layoutRes, parent) {

        private val rootView: ListItemFilesBinding get() = ListItemFilesBinding.bind(itemView)

        override fun updateView(item: SearchMedia) {
            rootView.run {
                item.run {
                    tvFileName.text = attachment.filename
                    tvFileDate.text = Calendar.getInstance().hourMinuteFormat()
                    //tvFileSize.text = fileSize
                }
            }
        }
    }

}