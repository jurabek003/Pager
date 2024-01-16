@file:OptIn(ExperimentalFoundationApi::class)

package uz.turgunboyevjurabek.pager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.pager.ui.theme.PagerTheme
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()) {
        val pagerState= rememberPagerState(pageCount = { 10 })
        val state = rememberLazyListState()

        HorizontalPager(verticalAlignment = Alignment.Bottom,state = pagerState, modifier = Modifier) {page ->
            Card(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(400.dp)
                .padding(20.dp)
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    alpha = if (state.isScrollInProgress) 0.5f else 1f

                }
            ) {
                Column {
                    val img= painterResource(id = R.drawable.img)
                    Image(painter =img , contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterHorizontally))
                    Text(
                        text = "Page: $page",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }
        /**
         * Sahifa holati o'zgarishi haqida xabar oling
         */
//        LaunchedEffect(pagerState) {
//            // Collect from the a snapshotFlow reading the currentPage
//            snapshotFlow { pagerState.currentPage }.collect { page ->
//                // Do something with each page change, for example:
//                // viewModel.sendPageSelectedEvent(page)
//                Log.d("Page change", "Page changed to $page")
//            }
//        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ){
            repeat(pagerState.pageCount){iteration->
                val color=if (pagerState.currentPage==iteration) Color.DarkGray else Color.LightGray
                Box(modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(16.dp))
            }
        }



        val coroutineScope = rememberCoroutineScope()

        Button(
            onClick = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(5)
            }
        },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text(text = "Jump to ...", color = Color.White)
        }



    }

}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    PagerTheme {
        Greeting()
    }
}