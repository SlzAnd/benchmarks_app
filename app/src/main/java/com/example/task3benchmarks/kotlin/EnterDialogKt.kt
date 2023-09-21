package com.example.task3benchmarks.kotlin

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.task3benchmarks.MyApplication
import com.example.task3benchmarks.databinding.DialogEnterBinding
import com.example.task3benchmarks.util.AppConstants
import com.google.android.material.textfield.TextInputEditText
import javax.inject.Inject

class EnterDialogKt : DialogFragment() {

    private var binding: DialogEnterBinding? = null
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
        binding = DialogEnterBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeStyleFromTextChanges(binding!!.dialogInput)
        binding!!.calculateButton.setOnClickListener {
            val input = binding!!.dialogInput
            var number = 0
            if (input.text.toString().isNotBlank()) {
                number = input.text.toString().toInt()
            }
            if (validateInput(number)) {
                val bundle = Bundle()
                bundle.putInt(AppConstants.EXTRA_KEY, number)
                parentFragmentManager.setFragmentResult(AppConstants.REQUEST_KEY, bundle)
                // START CALCULATION!!
                if (viewModel.isCollectionsTab) {
                    viewModel.onEvent(AppEvent.StartCollectionsCalculation)
                    viewModel.tabsFirstVisit[0] = false
                } else {
                    viewModel.onEvent(AppEvent.StartMapsCalculation)
                    viewModel.tabsFirstVisit[1] = false
                }
                viewModel.onEvent(AppEvent.SetIsCalculating(true))
            } else {
                binding!!.dialogInputLayout.error = "Error.You need enter elements count."
            }
        }
    }

    private fun validateInput(size: Int): Boolean {
        return size in 1_000_000..10_000_000
    }

    private fun changeStyleFromTextChanges(editText: TextInputEditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) {
                    // nothing entered -> hint is visible -> smaller size
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                } else {
                    // something entered -> hint moves to the top -> bigger size and black color
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                    editText.setTextColor(Color.BLACK)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}