package com.android.mysearch_application.presentation.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.android.mysearch_application.adapter.CollectionAdapter
import com.android.mysearch_application.adapter.SearchAdapter
import com.android.mysearch_application.data.Document
import com.android.mysearch_application.data.CollectItem
import com.android.mysearch_application.retrofit.NetWorkClient
import com.example.mysearch_application.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchAdapter by lazy { SearchAdapter { item -> toggleSelection(item) } }
    private val collectionAdapter by lazy { CollectionAdapter { item -> toggleSelection(item) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        restoreSearchQuery()

        binding.btnSearch.setOnClickListener {
            saveSearchQuery()
            fetchImages(createSearchParams(binding.etSearch.text.toString()))
            hideKeyboard(requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerViews() {
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchAdapter
        }
    }

    private fun saveSearchQuery() {
        val preferences = requireActivity().getSharedPreferences("search_prefs", Context.MODE_PRIVATE)
        with(preferences.edit()) {
            putString("last_search", binding.etSearch.text.toString())
            apply()
        }
    }

    private fun restoreSearchQuery() {
        val preferences = requireActivity().getSharedPreferences("search_prefs", Context.MODE_PRIVATE)
        binding.etSearch.setText(preferences.getString("last_search", ""))
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchImages(params: Map<String, String>) = lifecycleScope.launch {
        try {
            val response = NetWorkClient.searchNetWork.getSearch(params)
            searchAdapter.data = response.searchDocuments
            searchAdapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createSearchParams(query: String): Map<String, String> {
        return mapOf(
            "query" to query,
            "page" to "1",
            "size" to "80"
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun toggleSelection(document: Document) {
        val index = searchAdapter.data.indexOf(document)
        if (index != -1) {
            val updatedItems = searchAdapter.data.toMutableList()
            updatedItems[index] = document.copy(isSelected = !document.isSelected)
            searchAdapter.data = updatedItems
            searchAdapter.notifyDataSetChanged()

            if (document.isSelected) {
                CollectItem.removeItem(document)
            } else {
                CollectItem.addItem(document)
            }

            collectionAdapter.data = CollectItem.collectItems
            collectionAdapter.notifyDataSetChanged()
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }
}
