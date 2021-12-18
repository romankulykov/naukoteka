package uddug.com.naukoteka.ui.fragments.chat_flow.files

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import uddug.com.domain.entities.FilesEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemFilesBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class FilesAdapter :
    BaseAdapter<FilesEntity, FilesAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): FilesAdapter.ViewHolder =
        ViewHolder(R.layout.list_item_files, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<FilesEntity>(layoutRes, parent) {

        private val rootView: ListItemFilesBinding get() = ListItemFilesBinding.bind(itemView)

        override fun updateView(item: FilesEntity) {
            rootView.run {
                item.run {
                    tvFileName.text = fileName
                    tvFileDate.text = fileDate
                    tvFileSize.text = fileSize
                }
            }
        }
    }

}