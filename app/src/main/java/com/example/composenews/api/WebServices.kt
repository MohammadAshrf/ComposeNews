package com.example.composenews.api

import com.example.composenews.Constants
import com.example.composenews.api.model.newsResponse.NewsResponse
import com.example.composenews.api.model.sourcesResponse.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("v2/top-headlines/sources")
    fun getSources(
        @Query("apiKey") key: String = Constants.apiKey,

        @Query("category") category: String
    ): Call<SourcesResponse>

    @GET("v2/everything")
    fun getNews(
        @Query("apiKey") key: String = Constants.apiKey,
        @Query("sources") sources: String
    ): Call<NewsResponse>


}