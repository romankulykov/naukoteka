package uddug.com.naukoteka.ui.fragments.chat_flow.search_in_chapter.audio

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import uddug.com.domain.entities.AudioEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.ListItemAudioBinding
import uddug.com.naukoteka.global.base.BaseAdapter
import uddug.com.naukoteka.global.base.BaseViewHolder

class AudioAdapter :
    BaseAdapter<AudioEntity, AudioAdapter.ViewHolder>() {

    override fun newViewHolder(parent: ViewGroup, viewType: Int): AudioAdapter.ViewHolder =
        ViewHolder(R.layout.list_item_audio, parent)

    inner class ViewHolder(@LayoutRes layoutRes: Int, parent: ViewGroup) :
        BaseViewHolder<AudioEntity>(layoutRes, parent) {

        private val rootView: ListItemAudioBinding get() = ListItemAudioBinding.bind(itemView)

        override fun updateView(item: AudioEntity) {
            rootView.run {
                item.run {
                    tvAudioName.text = audioName
                    tvDurationAudio.text = durationAudio
                    tvAudioDate.text = audioDate
                }
            }
        }
    }

}