package com.android.mysearch_application.data

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("documents") val searchDocuments: List<Document>
)

data class Meta(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("pageable_count") val pageableCount: Int,
    @SerializedName("is_end") val isEnd: Boolean
)

data class Document(
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("display_sitename") val siteName: String,
    @SerializedName("datetime") val dateTime: String,
    var isSelected: Boolean = false
)
