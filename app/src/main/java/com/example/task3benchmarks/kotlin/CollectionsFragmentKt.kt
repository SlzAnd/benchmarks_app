package com.example.task3benchmarks.kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.task3benchmarks.MyApplication
import com.example.task3benchmarks.data.DataItem
import com.example.task3benchmarks.databinding.FragmentCollectionsBinding
import javax.inject.Inject

class CollectionsFragmentKt : BaseFragmentKt() {

    private var binding: FragmentCollectionsBinding? = null

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
        binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val composeView = binding?.collectionsComposeView
        super.setupGridLayout(
            viewModel.collectionsLiveData,
            viewModel,
            composeView
        )
    }

    override fun getItems(): MutableList<DataItem> {
        return viewModel.collectionsItems
    }

    override fun setItem(item: DataItem) {
        viewModel.onEvent(AppEvent.SetCollectionsItem(item))
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}