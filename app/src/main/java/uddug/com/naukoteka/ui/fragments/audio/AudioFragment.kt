package uddug.com.naukoteka.ui.fragments.audio

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uddug.com.domain.entities.AudioEntity
import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentAudioBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.presentation.audio.AudioPresenter
import uddug.com.naukoteka.presentation.audio.AudioView
import uddug.com.naukoteka.utils.BackButtonListener
import uddug.com.naukoteka.utils.viewBinding

class AudioFragment : BaseFragment(R.layout.fragment_audio),
    AudioView, BackButtonListener {

    @InjectPresenter
    lateinit var presenter: AudioPresenter

    @ProvidePresenter
    fun providePresenter(): AudioPresenter {
        return getScope().getInstance(AudioPresenter::class.java)
    }

    override val contentView by viewBinding(FragmentAudioBinding::bind)

    private val audioAdapter = AudioAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentView.run {
            rvAudio.adapter = audioAdapter
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.exit()
        return true
    }

    override fun showAudio(audio: List<AudioEntity>) {
        audioAdapter.setItems(audio)
    }
}