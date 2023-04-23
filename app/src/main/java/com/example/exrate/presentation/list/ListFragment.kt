package com.example.exrate.presentation.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.exrate.databinding.FragmentListBinding
import com.example.exrate.presentation.recycler.Adapter
import com.example.exrate.presentation.navigation.Navigator
import com.example.exrate.presentation.navigation.Screen
import com.example.exrate.presentation.show
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private val viewModel by viewModel<ListViewModel>()
    private var navigator: Navigator? = null
    private var binding: FragmentListBinding? = null
    private val adapter = Adapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = context as? Navigator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = binding ?: return
        binding.recycler.adapter = adapter
        initViewModel(binding)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDetach() {
        super.onDetach()
        navigator = null
    }

    private fun initViewModel(binding: FragmentListBinding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.items.collect { items ->
                        adapter.items = items
                    }
                }
                launch {
                    viewModel.loading.collect {
                        binding.progressBar.show(it)
                    }
                }
                launch {
                    viewModel.error.collect {
                        binding.errorMessage.show(it)
                    }
                }
                launch {
                    viewModel.openDetails.collect { event ->
                        openDetails(event.coinId)
                    }
                }
            }
        }
    }

    private fun openDetails(id: Int) {
        val screen = Screen.Details(id)
        navigator?.navigateTo(screen)
    }

}