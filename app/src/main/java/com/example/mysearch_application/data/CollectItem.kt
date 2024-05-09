package com.android.mysearch_application.data

import android.util.Log

object CollectItem {
    private val _collectItems = mutableListOf<Document>()
    val collectItems: List<Document> get() = _collectItems

    fun addItem(data: Document) {
        if (!_collectItems.contains(data)) {
            _collectItems.add(data)
            Log.d("add_collectItems", "$_collectItems")
        }
    }

    fun removeItem(data: Document) {
        _collectItems.removeAll { it.thumbnailUrl == data.thumbnailUrl }
        Log.d("remove_collectItems", "$_collectItems")
    }
}
