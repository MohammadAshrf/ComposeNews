package com.example.composenews.ui.news

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.composenews.R
import com.example.composenews.api.model.newsResponse.News
import com.example.composenews.api.model.sourcesResponse.Source

const val NEWS_ROUTE = "news"

@Composable
fun NewsFragment(
    category: String?,
    viewModel: NewsViewModel = viewModel(),
) {

    viewModel.getNewsSources(category, viewModel.sourcesList)

    Column {
        NewsSourcesTabs(viewModel.sourcesList, viewModel.newsList)
        NewsList(viewModel.newsList ?: listOf())
    }
}

@Composable
fun NewsList(news: List<News?>) {
    LazyColumn {
        items(news.size) {
            NewsCard(articlesItem = news[it])
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(articlesItem: News?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 12.dp)
    ) {
        GlideImage(
            model = articlesItem?.urlToImage, contentDescription = "News Picture",
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = articlesItem?.author ?: "",
            style = TextStyle(color = colorResource(id = R.color.grey))
        )
        Text(
            text = articlesItem?.title ?: "",
            style = TextStyle(color = colorResource(id = R.color.colorBlack))
        )
        Text(
            text = articlesItem?.publishedAt ?: "",
            style = TextStyle(color = colorResource(id = R.color.grey2))
        )


    }
}


@Composable
fun NewsSourcesTabs(
    sourcesItemList: List<Source?>,
    newsResponseState: List<News?>,
    viewModel: NewsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    viewModel.shouldShowLoading = true

    if (sourcesItemList.isNotEmpty())
        ScrollableTabRow(
            selectedTabIndex = viewModel.selectedIndex,
            containerColor = Color.Transparent,
            divider = {},
            indicator = {},
            modifier = modifier
        ) {
            sourcesItemList.forEachIndexed { index, sourceItem ->
                if (viewModel.selectedIndex == index)
                    viewModel.getNews(sourceItem!!, newsResponseState)
                Tab(
                    selected = viewModel.selectedIndex == index,
                    onClick = {
                        viewModel.shouldShowLoading = false
                        viewModel.selectedIndex = index
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color(0xFF39A552),
                    modifier =
                    if (viewModel.selectedIndex == index)
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
                    text = { Text(text = sourceItem?.name ?: "") }
                )

            }

        }

}

