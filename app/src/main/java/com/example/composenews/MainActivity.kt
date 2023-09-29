package com.example.composenews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.composenews.api.model.sourcesResponse.Source
import com.example.composenews.ui.theme.ComposeNewsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeNewsTheme {
                val sourcesList: MutableLiveData<List<Source>> = remember {
                    MutableLiveData(listOf())
                }

                sourcesList.value?.let { NewsSourcesTabs(sourcesItemList = it) }
            }
        }
    }

    @Composable
    fun NewsSourcesTabs(sourcesItemList: List<Source>) {
        var selectedIndex = 0
        ScrollableTabRow(selectedTabIndex = selectedIndex, containerColor = Color.Transparent) {
            sourcesItemList.forEachIndexed { index, sourceItem ->
                Tab(
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color(0xFF39A552),
                    modifier =
                    if (selectedIndex == index)
                        Modifier
                            .padding(end = 2.dp)
                            .background(
                                Color(0xFF39A552),
                                RoundedCornerShape(50)
                            )
                    else
                        Modifier
                            .padding(end = 2.dp)
                            .border(2.dp, Color(0xFF39A552), RoundedCornerShape(50)),
                    text = { Text(text = sourceItem.name ?: "") }
                )

            }

        }

    }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        ComposeNewsTheme {
            NewsSourcesTabs(
                sourcesItemList = listOf(
                    Source(name = "ABC News"),
                    Source(name = "ABC News"),
                    Source(name = "ABC News"),
                    Source(name = "ABC News"),
                    Source(name = "ABC News")
                )
            )
        }

    }
}


