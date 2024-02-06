package com.application.traverldiary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.traverldiary.R
import com.application.traverldiary.databinding.FragmentAlbumBinding
import com.application.traverldiary.views.adapter.DateAlbumAdapter

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAlbumBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val albumList = ArrayList<Any>()
        for (i in 1..4) {
            albumList.add("2024-1-${i}")
            for (j in 0..11) {
                albumList.add(R.drawable.progress as Int)
            }
        }
        for (i in 5..10) {
            albumList.add("2024-1-${i}")
            for (j in 0..11) {
                albumList.add(R.drawable.gao_plant as Int)
            }
        }
        val mAdapter = DateAlbumAdapter()
        mAdapter.mList = albumList
        val gridLayoutManager = GridLayoutManager(context,5)
        val mRecyclerView: RecyclerView = binding.recyclerviewMyAlbum
        mRecyclerView.apply {
            layoutManager = gridLayoutManager

            adapter = mAdapter

        }
    }

}