//package com.example.task3benchmarks;
//
//import static com.example.task3benchmarks.util.AppConstants.EXTRA_KEY;
//import static com.example.task3benchmarks.util.AppConstants.REQUEST_KEY;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.viewpager2.widget.ViewPager2;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.example.task3benchmarks.databinding.ActivityMainBinding;
//import com.example.task3benchmarks.presentation.ViewPagerAdapter;
//import com.google.android.material.tabs.TabLayout;
//import com.google.android.material.textfield.TextInputEditText;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    private ActivityMainBinding binding = null;
//    AppViewModel viewModel;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fragmentManager, getLifecycle());
//
//        setContentView(binding.getRoot());
//
//        // widgets
//        TabLayout tabLayout = binding.tabLayout;
//        ViewPager2 viewPager = binding.viewPager;
//        TextInputEditText startStopInput = binding.includeStartStopField.startStopInput;
//        Button startStopButton = binding.includeStartStopField.startStopButton;
//
//        tabLayout.addTab(tabLayout.newTab().setText("Collections"));
//        tabLayout.addTab(tabLayout.newTab().setText("Maps"));
//
//        viewPager.setAdapter(viewPagerAdapter);
//
//        if (viewModel.tabsFirstVisit[tabLayout.getSelectedTabPosition()]) {
//            showEnterDialog();
//        } else {
//            showContent(tabLayout.getSelectedTabPosition());
//        }
//
//        // get size value from Dialog window(using ResultAPI) -> update viewModel state
//        fragmentManager.setFragmentResultListener(REQUEST_KEY, this, ((requestKey, bundle) -> {
//            viewModel.size = bundle.getInt(EXTRA_KEY);
//            String stringResult = String.valueOf(viewModel.size);
//            startStopInput.setText(stringResult);
//            showContent(tabLayout.getSelectedTabPosition());
//        }));
//
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) {
//                    viewModel.isCollectionsTab = true;
//                } else {
//                    viewModel.isCollectionsTab = false;
//                }
//
//                if (viewModel.tabsFirstVisit[tab.getPosition()]) {
//                    showEnterDialog();
//                } else {
//                    showContent(tab.getPosition());
//                }
//                viewPager.setCurrentItem(tab.getPosition(), false);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
//
//        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 0) {
//                    tabLayout.setSelectedTabIndicator(R.drawable.tab_indicator_left);
//                } else {
//                    tabLayout.setSelectedTabIndicator(R.drawable.tab_indicator_right);
//                }
//                tabLayout.selectTab(tabLayout.getTabAt(position));
//            }
//        });
//
//        // Start-Stop Field
//
//        startStopInput.setOnClickListener(v -> showEnterDialog());
//
//        viewModel.getIsCalculating().observe(this, isCalculating -> {
//            if (isCalculating) {
//                startStopButton.setText(R.string.stop_button_text);
//                startStopButton.setBackgroundTintList(getColorStateList(R.color.black));
//            } else {
//                startStopButton.setText(R.string.start_button_text);
//                startStopButton.setBackgroundTintList(getColorStateList(R.color.web_orange));
//            }
//        });
//
//        startStopButton.setOnClickListener(v -> {
//            // if calculations running
//            if (viewModel.getIsCalculating().getValue() != null &&
//                    viewModel.getIsCalculating().getValue()
//            ) {
//                if (viewModel.isCollectionsTab) {
//                    viewModel.stopCollectionsCalculation();
//                } else {
//                    viewModel.stopMapsCalculations();
//                }
//                viewModel.setIsCalculating(false);
//                // if calculations stopped
//            } else if (viewModel.getIsCalculating().getValue() != null) {
//                if (viewModel.isCollectionsTab) {
//                    viewModel.startCollectionsCalculation();
//                } else {
//                    viewModel.startMapsCalculations();
//                }
//                viewModel.setIsCalculating(true);
//            }
//        });
//    }
//
//    private void showEnterDialog() {
//        binding.viewPager.setVisibility(View.GONE);
//        binding.includeStartStopField.startStopLayout.setVisibility(View.GONE);
//        binding.dialogContainer.setVisibility(View.VISIBLE);
//        binding.toolbarTitle.setText(R.string.activity);
//    }
//
//    private void showContent(int currentTabIndex) {
//        binding.dialogContainer.setVisibility(View.GONE);
//        binding.viewPager.setVisibility(View.VISIBLE);
//        binding.includeStartStopField.startStopLayout.setVisibility(View.VISIBLE);
//        binding.toolbarTitle.setText(R.string.collectionAndMaps);
//        binding.viewPager.setCurrentItem(currentTabIndex, false);
//    }
//}