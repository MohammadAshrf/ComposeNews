package com.example.composenews.ui.news

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.composenews.Constants
import com.example.composenews.ViewError
import com.example.composenews.api.ApiManager
import com.example.composenews.api.model.newsResponse.News
import com.example.composenews.api.model.newsResponse.NewsResponse
import com.example.composenews.api.model.sourcesResponse.Source
import com.example.composenews.api.model.sourcesResponse.SourcesResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    var shouldShowLoading by mutableStateOf(false)
    var sourcesList by mutableStateOf<List<Source?>>(listOf())
    var newsList by mutableStateOf<List<News?>>(listOf())
    var errorState by mutableStateOf(ViewError())
    var selectedIndex by mutableIntStateOf(0)


    fun getNewsSources(
        category: String?,
        sourceItem: List<Source?>?,

        ) {

        ApiManager
            .getApis()
            .getSources(Constants.apiKey, category ?: "")
            .enqueue(object : Callback<SourcesResponse> {
                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    //thread safe better than .set value

                    errorState = (
                            ViewError(
                                throwable = t
                            ) { getNewsSources(category, sourceItem) })

//                    handleError(t) {
//                        getNewsSources()
//                    }
                }

                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    shouldShowLoading = (false)
                    if (response.isSuccessful) {
                        //show tabs in fragment
//                        bindTabs(response.body()?.sources)
                        sourcesList = response.body()?.sources ?: listOf()
                    } else {
                        val errorBodyJsonString = response.errorBody()?.string()
                        val response =
                            Gson().fromJson(errorBodyJsonString, SourcesResponse::class.java)
                        errorState = (
                                ViewError(
                                    message = response.message
                                ) { getNewsSources(category, sourceItem) })
//                        handleError(response.message) {
//                            getNewsSources()
//                        }
                    }
                }
            })
    }

    fun getNews(sourceItem: Source, newsResponseState: List<News?>?) {
        shouldShowLoading = (true)
        ApiManager.getApis()
            .getNews(Constants.apiKey, sourceItem.id ?: "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    shouldShowLoading = (false)
                    errorState = (
                            ViewError(
                                throwable = t
                            ) { getNews(sourceItem, newsResponseState) }
                            )
//                    handleError(t) {
//                        getNews(sourceId)
//                    }
                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    shouldShowLoading = (false)
                    if (response.isSuccessful) {
                        //show news
//                        adapter.bindViews(response.body()?.articles)
                        newsList = response.body()?.articles ?: listOf()

                        return
                    }
                    val responseJsonError = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(responseJsonError, NewsResponse::class.java)
                    errorState = (
                            ViewError(
                                message = errorResponse.message
                            ) { getNews(sourceItem, newsResponseState) }
                            )
//                    handleError(errorResponse.message) {
//                        getNews(sourceId)
//                    }


                }
            })
    }


}