package com.example.task3benchmarks.kotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.task3benchmarks.MyApplication
import com.example.task3benchmarks.R
import com.example.task3benchmarks.databinding.ActivityMainBinding
import com.example.task3benchmarks.ui.theme.poppinsFontFamily
import com.example.task3benchmarks.ui.theme.webOrange
import com.example.task3benchmarks.util.AppConstants
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import javax.inject.Inject

class MainActivityKt : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: AppViewModelKt
    private lateinit var toolbarTitle: MutableState<String>


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, viewModelFactory)[AppViewModelKt::class.java]
        val fragmentManager = supportFragmentManager
        val viewPagerAdapter = ViewPagerAdapterKt(fragmentManager, lifecycle)
        toolbarTitle = mutableStateOf("Activity")

        val startStopButtonText: MutableState<String> = mutableStateOf("Start")
        val startStopButtonColor: MutableState<Color> = mutableStateOf(webOrange)

        setContentView(binding.root)

        binding.toolbarComposeView.setContent {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = toolbarTitle.value,
                            color = colorResource(id = R.color.white),
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight(500)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.web_orange)
                )
            )
        }

        // widgets
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

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
        binding.startStopFieldComposeView.setContent {
            ConstraintLayout(
                modifier = Modifier
                    .padding(top = 24.dp, start = 26.dp),
            ) {
                val (textField, button) = createRefs()

                TextField(
                    modifier = Modifier
                        .width(315.dp)
                        .height(64.dp)
                        .clickable {
                            showEnterDialog()
                        }
                        .constrainAs(textField) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                    value = viewModel.size.toString(),
                    enabled = false,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight(500),
                        color = colorResource(id = R.color.black)
                    ),
                    colors = TextFieldDefaults.colors(
                        disabledContainerColor = colorResource(id = R.color.grey_10),
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    label = {
                        Text(
                            text = stringResource(id = R.string.start_stop_hint),
                            fontSize = 14.sp,
                            fontWeight = FontWeight(500),
                            fontFamily = poppinsFontFamily,
                            color = colorResource(id = R.color.grey),
                        )
                    },
                    onValueChange = {}
                )

                Button(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .width(127.dp)
                        .height(62.dp)
                        .zIndex(2f)
                        .constrainAs(button) {
                            top.linkTo(parent.top, 1.dp)
                            start.linkTo(textField.end, -(90.dp))
                        },
                    colors = ButtonDefaults
                        .buttonColors(startStopButtonColor.value),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
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
                ) {
                    Text(
                        text = startStopButtonText.value,
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500)
                    )
                }
            }
        }

        viewModel.isCalculating.observe(
            this
        ) { isCalculating: Boolean ->
            if (isCalculating) {
                startStopButtonText.value = "Stop"
                startStopButtonColor.value = Color.Black
            } else {
                startStopButtonText.value = "Start"
                startStopButtonColor.value = webOrange
            }
        }
    }

    private fun showEnterDialog() {
        binding.viewPager.visibility = View.GONE
        binding.startStopFieldComposeView.visibility = View.GONE
        binding.dialogContainer.visibility = View.VISIBLE
        toolbarTitle.value = "Activity"
    }

    private fun showContent(currentTabIndex: Int) {
        binding.dialogContainer.visibility = View.GONE
        binding.viewPager.visibility = View.VISIBLE
        binding.startStopFieldComposeView.visibility = View.VISIBLE
        toolbarTitle.value = "Collection & Maps"
        binding.viewPager.setCurrentItem(currentTabIndex, false)
    }
}