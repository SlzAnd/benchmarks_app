package com.example.task3benchmarks;

import static com.example.task3benchmarks.util.AppConstants.EXTRA_KEY;
import static com.example.task3benchmarks.util.AppConstants.REQUEST_KEY;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.task3benchmarks.databinding.DialogEnterBinding;
import com.google.android.material.textfield.TextInputEditText;


public class EnterDialog extends DialogFragment {

    public static String TAG = "EnterDialog";
    private DialogEnterBinding binding = null;
    private AppViewModel viewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogEnterBinding.inflate(inflater, container, false);
        changeStyleFromTextChanges(binding.dialogInput);

        binding.calculateButton.setOnClickListener(v -> {
            TextInputEditText input = binding.dialogInput;
            int number = 0;
            if (!input.getText().toString().isBlank()) {
                number = Integer.parseInt(input.getText().toString());
            }
            if (validateInput(number)) {
                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_KEY, number);
                getParentFragmentManager().setFragmentResult(REQUEST_KEY, bundle);
                // START CALCULATION!!
                if (viewModel.isCollectionsTab) {
                    viewModel.startCollectionsCalculation();
                    viewModel.tabsFirstVisit[0] = false;
                } else {
                    viewModel.startMapsCalculations();
                    viewModel.tabsFirstVisit[1] = false;
                }
                viewModel.getIsCalculating().setValue(true);
            } else {
                binding.dialogInputLayout.setError("Error.You need enter elements count.");
            }
        });
        return binding.getRoot();
    }

    private boolean validateInput(int size) {
        return size >= 1_000_000 && size <= 10_000_000;
    }


    private void changeStyleFromTextChanges(TextInputEditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    // nothing entered -> hint is visible -> smaller size
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                } else {
                    // something entered -> hint moves to the top -> bigger size and black color
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    editText.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
    }
}
