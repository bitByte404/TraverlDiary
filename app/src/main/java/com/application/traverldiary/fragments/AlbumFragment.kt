package com.application.traverldiary.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.traverldiary.databinding.FragmentAlbumBinding
import com.application.traverldiary.manager.AlbumManager
import com.application.traverldiary.manager.PermissionManager
import com.application.traverldiary.views.adapter.DateAlbumAdapter
import java.io.File

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    private lateinit var mAlbumManager:AlbumManager
    private lateinit var mPermissionManager:PermissionManager
    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri = result.data?.data
            if (selectedImageUri != null) {
                val sourceFile = File(mAlbumManager.getRealPathFromURI(requireContext(),selectedImageUri))
                mAlbumManager.copyFile(sourceFile)
                Log.v("wq","copy")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAlbumBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //manager
        mAlbumManager = AlbumManager.getInstance(requireContext().filesDir.absolutePath)
        mPermissionManager = PermissionManager.getInstance()
        val albumList = mAlbumManager.loadPictures(requireContext())

        //配置 recyclerview 照片
        val mAdapter = DateAlbumAdapter()
        mAdapter.mList = albumList
        val gridLayoutManager = GridLayoutManager(context,5)
        val mRecyclerView: RecyclerView = binding.recyclerviewMyAlbum
        mRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = mAdapter
        }

        binding.addPic.setOnClickListener(View.OnClickListener {
            if (true) {
                openImageSelector()
            }else{
                return@OnClickListener
            }
        })

    }

    //打开图片选择的Intent
    private fun openImageSelector() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        selectImageLauncher.launch(intent)
    }


}