package com.application.traveldiary.views.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.R
import com.application.traveldiary.databinding.FragmentAlbumBinding
import com.application.traveldiary.manager.AlbumManager
import com.application.traveldiary.manager.PermissionManager
import com.application.traveldiary.viewModel.AlbumViewModel
import com.application.traveldiary.adapter.DateAlbumAdapter
import com.application.traveldiary.views.selif_define_views.PhotoView

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    private lateinit var mAlbumManager: AlbumManager
    private lateinit var mPermissionManager: PermissionManager
    private val mViewModel:AlbumViewModel by activityViewModels()
    //图片选择启动器
    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri = result.data?.data
            if (selectedImageUri != null) {
                mAlbumManager.addPicture(selectedImageUri,requireContext())
                mViewModel.albumList.postValue(mAlbumManager.loadPictures(requireContext()))
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
        //初始化 赋值
        mAlbumManager = AlbumManager.getInstance(requireContext().filesDir.absolutePath)
        mPermissionManager = PermissionManager.getInstance()
        val mAdapter = DateAlbumAdapter()
        val mRecyclerView: RecyclerView = binding.recyclerviewMyAlbum

        mAdapter.callback = {
            mViewModel.picture = it
            findNavController().navigate(R.id.action_mainFragment_to_pictureFragment)

        }
        //配置layoutmanager
        val gridLayoutManager = GridLayoutManager(context,mAdapter.spanCount)
        //配置recyclerview
        mRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = mAdapter
        }

        // 观察LiveData的数据变化
        mViewModel.albumList.observe(viewLifecycleOwner, Observer { newData ->
            // 当数据变化时，更新Adapter的数据
            mAdapter.updateData(newData)
        })
        mViewModel.albumList.postValue(mAlbumManager.loadPictures(requireContext()))

        //为添加图片按钮添加方法
        binding.addPic.setOnClickListener(View.OnClickListener {
            openImageSelector()
        })

        //刷新逻辑
        binding.swipeRefreshLayout.setOnRefreshListener {
            mViewModel.albumList.postValue(mAlbumManager.loadPictures(requireContext()))
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    //打开图片选择的Intent
    private fun openImageSelector() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        selectImageLauncher.launch(intent)
    }

    override fun onPause() {
        super.onPause()
        System.gc()
    }

    fun popPicAlertDialog(pic:Uri){
        val photo = PhotoView(requireContext(),null)
        photo.setPic(pic)
        AlertDialog.Builder(context)
            .setView(photo)
            .create()
            .show()
    }
}