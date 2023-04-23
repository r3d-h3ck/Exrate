package com.example.exrate.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.exrate.R
import com.example.exrate.databinding.FragmentDetailsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment(R.layout.fragment_details) {

    companion object {

        private const val COIN_ID = "coin_id"

        fun newInstance(coinId: Int): DetailsFragment {
            return DetailsFragment().apply {
                arguments = Bundle(1).apply {
                    putInt(COIN_ID, coinId)
                }
            }
        }

    }

    private val coinId: Int by lazy { arguments?.getInt(COIN_ID) ?: -1 }
    private val viewModel by viewModel<DetailsViewModel> { parametersOf(coinId) }
    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = binding ?: return
        initViewModel(binding)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initViewModel(binding: FragmentDetailsBinding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.coinName.collect { name ->
                        val message = getString(R.string.details_placeholder, name.uppercase())
                        binding.message.text = message
                    }
                }
                launch {
                    viewModel.error.collect {
                        binding.message.text = getString(R.string.failed_to_fetch_data_error)
                    }
                }
            }
        }
    }

}