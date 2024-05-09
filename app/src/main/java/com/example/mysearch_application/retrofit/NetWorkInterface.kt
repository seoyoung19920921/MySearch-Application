package com.android.mysearch_application.retrofit


import com.android.mysearch_application.data.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @Headers("Authorization: KakaoAK b6516a8fc1305bc82d214baf7780a346")
    @GET("/v2/search/image")
    suspend fun getSearch(@QueryMap param: Map<String, String>): SearchResponse
}