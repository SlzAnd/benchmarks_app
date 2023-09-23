package com.example.task3benchmarks.kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.task3benchmarks.MyApplication
import com.example.task3benchmarks.R
import com.example.task3benchmarks.databinding.DialogEnterBinding
import com.example.task3benchmarks.ui.theme.poppinsFontFamily
import com.example.task3benchmarks.util.AppConstants
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
        binding!!.enterDialogComposeView!!.setContent {
            var inputSize by remember { mutableStateOf("") }
            var isError by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 35.dp, start = 30.dp, end = 30.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.enter_dialog_collections_message),
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight(400),
                    color = colorResource(id = R.color.black)
                )

                Spacer(modifier = Modifier.height(21.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = inputSize,
                    onValueChange = { inputSize = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(500),
                        color = colorResource(id = R.color.black)
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(id = R.color.grey_10),
                        unfocusedContainerColor = colorResource(id = R.color.grey_10),
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    isError = isError,
                    supportingText = {
                        if (isError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = R.string.dialog_err_message),
                            )
                        }
                    },
                    label = {
                        Text(
                            modifier = Modifier
                                .padding(top = 7.dp),
                            text = stringResource(id = R.string.dialog_input_collections_hint_text),
                            fontSize = 14.sp,
                            fontWeight = FontWeight(500),
                            fontFamily = poppinsFontFamily,
                            color = colorResource(id = R.color.grey),
                        )
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp),
                    colors = ButtonDefaults
                        .buttonColors(colorResource(id = R.color.web_orange)),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        val number = if (inputSize.isNotBlank()) {
                            inputSize.toInt()
                        } else 0
                        if (validateInput(number)) {
                            val bundle = Bundle()
                            bundle.putInt(AppConstants.EXTRA_KEY, number)
                            parentFragmentManager.setFragmentResult(
                                AppConstants.REQUEST_KEY,
                                bundle
                            )
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
                            isError = true
                        }
                    }
                ) {
                    Text(text = stringResource(id = R.string.dialog_btn_text))
                }

                Spacer(modifier = Modifier.height(39.dp))
            }
        }
    }

    private fun validateInput(size: Int): Boolean {
        return size in 1_000_000..10_000_000
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}