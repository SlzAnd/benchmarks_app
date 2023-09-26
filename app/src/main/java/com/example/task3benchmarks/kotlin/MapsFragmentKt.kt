package com.example.task3benchmarks.kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModelProvider
import com.example.task3benchmarks.MyApplication
import com.example.task3benchmarks.data.DataItem
import com.example.task3benchmarks.databinding.FragmentMapsBinding
import javax.inject.Inject

class MapsFragmentKt : BaseFragmentKt() {
    private var binding: FragmentMapsBinding? = null

    private lateinit var viewModel: AppViewModelKt

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[AppViewModelKt::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val composeView = binding?.mapsComposeView
        super.setupGridLayout(
            viewModel.mapsLiveData,
            viewModel,
            composeView
        )
    }

    override fun getItems(): MutableList<DataItem> {
        return viewModel.mapsItems
    }

    override fun setItem(item: DataItem) {
        viewModel.onEvent(AppEvent.SetMapsItem(item))
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}