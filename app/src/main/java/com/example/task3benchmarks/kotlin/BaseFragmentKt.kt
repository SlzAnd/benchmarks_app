package com.example.task3benchmarks.kotlin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.task3benchmarks.R
import com.example.task3benchmarks.data.DataItem
import com.example.task3benchmarks.presentation.util.CircularProgressIndicatorWithGradient
import com.example.task3benchmarks.ui.theme.poppinsFontFamily


abstract class BaseFragmentKt : Fragment() {

    abstract fun getItems(): List<DataItem>

    abstract fun setItem(item: DataItem)

    open fun setupGridLayout(
        liveData: LiveData<DataItem>,
        viewModel: AppViewModelKt,
        gridComposeView: ComposeView?
    ) {
        gridComposeView?.setContent {
            var dataItems by remember {
                mutableStateOf(getItems())
            }

            var showBar by remember {
                mutableStateOf(true)
            }

            viewModel.isCalculating.observe(viewLifecycleOwner) { isCalculating: Boolean ->
                if (isCalculating) {
                    showBar = true
                    dataItems = getItems()
                } else {
                    val updatedDataItems = dataItems.toMutableList()
                    updatedDataItems.forEach { dataItem ->
                        dataItem.isCalculating = false
                        setItem(dataItem)
                    }
                    dataItems = updatedDataItems
                    showBar = false
                }
            }

            liveData.observe(
                viewLifecycleOwner
            ) { dataItem: DataItem? ->
                if (dataItem != null) {
                    val itemPosition = dataItem.id
                    val updatedDataItems = dataItems.toMutableList()
                    updatedDataItems[itemPosition] = dataItem
                    dataItems = updatedDataItems
                    setItem(dataItem)
                }
            }

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(27.dp),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(11.dp),
                horizontalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                items(
                    dataItems,
                    key = { dataItem -> dataItem.id }
                ) { dataItem ->
                    GridItem(dataItem = dataItem, showBar = showBar)
                }
            }
        }


    }
}

@Composable
fun GridItem(
    modifier: Modifier = Modifier,
    dataItem: DataItem,
    showBar: Boolean
) {

    Box(
        modifier = modifier
            .width(105.dp)
            .height(105.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(colorResource(id = R.color.grey_10)),
        contentAlignment = Alignment.Center
    ) {
        var time = "N/A"
        if (dataItem.time != null) {
            time = dataItem.time.toString()
        }
        if (dataItem.isCalculating && showBar) {
            CircularProgressIndicatorWithGradient(
                strokeWidth = 15.dp,
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xffFFFFFF),
                        Color(0xffFFC077),
                        Color(0xffBD522E)
                    )
                )
            )
        }
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            text = "${dataItem.operation.name} ${dataItem.dataStructure} $time ms",
            textAlign = TextAlign.Center,
            fontFamily = poppinsFontFamily,
            fontSize = 10.sp,
            fontWeight = FontWeight(400),
        )
    }
}
