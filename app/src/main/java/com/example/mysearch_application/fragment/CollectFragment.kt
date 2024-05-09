package com.example.mysearch_application.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.android.mysearch_application.adapter.CollectionAdapter
import com.android.mysearch_application.data.CollectItem
import com.android.mysearch_application.data.Document
import com.example.mysearch_application.databinding.FragmentCollectBinding

class CollectFragment : Fragment() {
    private var _binding: FragmentCollectBinding? = null
    private val binding get() = _binding!!
    private val collectionAdapter by lazy {
        CollectionAdapter { selectedItem -> adapterOnClick(selectedItem) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvCollect.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = collectionAdapter
        }
        updateCollectionData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun adapterOnClick(selectedItem: Document) {
        val index = collectionAdapter.data.indexOf(selectedItem)
        if (index != -1) {
            val updatedItems = collectionAdapter.data.toMutableList()
            updatedItems[index] = selectedItem.copy(isSelected = !selectedItem.isSelected)
            collectionAdapter.data = updatedItems
            collectionAdapter.notifyDataSetChanged()

            if (updatedItems[index].isSelected) {
                CollectItem.addItem(updatedItems[index])
            } else {
                CollectItem.removeItem(updatedItems[index])
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateCollectionData() {
        collectionAdapter.data = CollectItem.collectItems
        collectionAdapter.notifyDataSetChanged()
    }
}
