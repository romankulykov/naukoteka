package uddug.com.naukoteka.ui.fragments

import uddug.com.naukoteka.R
import uddug.com.naukoteka.databinding.FragmentTabContainerBinding
import uddug.com.naukoteka.global.base.BaseFragment
import uddug.com.naukoteka.utils.viewBinding

class StubFragment : BaseFragment(R.layout.fragment_tab_container) {

    override val contentView by viewBinding(FragmentTabContainerBinding::bind)


}