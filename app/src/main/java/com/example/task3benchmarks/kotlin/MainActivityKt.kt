package com.example.task3benchmarks.kotlin

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.task3benchmarks.MyApplication
import com.example.task3benchmarks.R
import com.example.task3benchmarks.databinding.ActivityMainBinding
import com.example.task3benchmarks.util.AppConstants
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import javax.inject.Inject

class MainActivityKt : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: AppViewModelKt

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, viewModelFactory)[AppViewModelKt::class.java]
        val fragmentManager = supportFragmentManager
        val viewPagerAdapter = ViewPagerAdapterKt(fragmentManager, lifecycle)

        setContentView(binding.root)

        // widgets
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        val startStopInput = binding.includeStartStopField.startStopInput
        val startStopButton: Button = binding.includeStartStopField.startStopButton

        tabLayout.addTab(tabLayout.newTab().setText("Collections"))
        tabLayout.addTab(tabLayout.newTab().setText("Maps"))

        viewPager.adapter = viewPagerAdapter

        if (viewModel.tabsFirstVisit[tabLayout.selectedTabPosition]) {
            showEnterDialog()
        } else {
            showContent(tabLayout.selectedTabPosition)
        }

        // get size value from Dialog window(using ResultAPI) -> update viewModel state
        fragmentManager.setFragmentResultListener(
            AppConstants.REQUEST_KEY, this
        ) { _: String?, bundle: Bundle ->
            viewModel.size = bundle.getInt(AppConstants.EXTRA_KEY)
            val stringResult = viewModel.size.toString()
            startStopInput.setText(stringResult)
            showContent(tabLayout.selectedTabPosition)
        }

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.isCollectionsTab = tab.position == 0

                if (viewModel.tabsFirstVisit[tab.position]) {
                    showEnterDialog()
                } else {
                    showContent(tab.position)
                }
                viewPager.setCurrentItem(tab.position, false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    tabLayout.setSelectedTabIndicator(R.drawable.tab_indicator_left)
                } else {
                    tabLayout.setSelectedTabIndicator(R.drawable.tab_indicator_right)
                }
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        // Start-Stop Field
        startStopInput.setOnClickListener { showEnterDialog() }

        viewModel.isCalculating.observe(
            this
        ) { isCalculating: Boolean ->
            if (isCalculating) {
                startStopButton.setText(R.string.stop_button_text)
                startStopButton.backgroundTintList = getColorStateList(R.color.black)
            } else {
                startStopButton.setText(R.string.start_button_text)
                startStopButton.backgroundTintList = getColorStateList(R.color.web_orange)
            }
        }

        startStopButton.setOnClickListener {
            // if calculations running
            if (viewModel.isCalculating.value != null &&
                viewModel.isCalculating.value!!
            ) {
                if (viewModel.isCollectionsTab) {
                    viewModel.onEvent(AppEvent.StopCollectionsCalculation)
                } else {
                    viewModel.onEvent(AppEvent.StopMapsCalculation)
                }
                viewModel.onEvent(AppEvent.SetIsCalculating(false))
                // if calculations stopped
            } else if (viewModel.isCalculating.value != null) {
                if (viewModel.isCollectionsTab) {
                    viewModel.onEvent(AppEvent.StartCollectionsCalculation)
                } else {
                    viewModel.onEvent(AppEvent.StartMapsCalculation)
                }
                viewModel.onEvent(AppEvent.SetIsCalculating(true))
            }
        }
    }

    private fun showEnterDialog() {
        binding.viewPager.visibility = View.GONE
        binding.includeStartStopField.startStopLayout.visibility = View.GONE
        binding.dialogContainer.visibility = View.VISIBLE
        binding.toolbarTitle.setText(R.string.activity)
    }

    private fun showContent(currentTabIndex: Int) {
        binding.dialogContainer.visibility = View.GONE
        binding.viewPager.visibility = View.VISIBLE
        binding.includeStartStopField.startStopLayout.visibility = View.VISIBLE
        binding.toolbarTitle.setText(R.string.collectionAndMaps)
        binding.viewPager.setCurrentItem(currentTabIndex, false)
    }
}