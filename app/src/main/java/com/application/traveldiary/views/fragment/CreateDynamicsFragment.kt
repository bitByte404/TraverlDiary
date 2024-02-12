package com.application.traveldiary.views.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.application.traveldiary.adapter.DynamicPictureAdapter
import com.application.traveldiary.databinding.FragmentCreateDynamicsBinding
import com.application.traveldiary.databinding.LayoutAddImagesViewBinding
import com.bumptech.glide.Glide
import java.io.File

class CreateDynamicsFragment : Fragment() {
    private lateinit var binding: FragmentCreateDynamicsBinding
    private lateinit var imageUri: Uri // 图片的Uri
    private lateinit var outputImage: File // 输出的图片文件
    val imageViews = mutableListOf<ImageView>() //存储ImageView的列表
    val imageViewUri = listOf<String>()

    // 初测一个启动活动的结果回调，用于处理拍照后的结果
    private val takePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

            }
        }


    //注册启动活动的结果回调，用于处理从相册选择图片
    private val fromAlbumLauncher =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val clipData = result.data?.clipData
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    // 创建一个新的ImageView
                    val imageView = ImageView(requireContext())
                    // 使用Glide加载图片
                    Glide.with(this)
                        .load(uri)
                        .centerCrop()
                        .into(imageView)
                    // 将ImageView添加到列表中
                    imageViews.add(imageView)
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateDynamicsBinding.inflate(inflater)
        touchEvent()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initData() {
        //配置动态的图片
        val pictureAdapter = DynamicPictureAdapter()
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 3)
        binding.recyclerView.adapter = pictureAdapter
        val addImagesView = LayoutAddImagesViewBinding.inflate(layoutInflater).root
    }

    private fun touchEvent() {
        binding.takePhotoBtn.setOnClickListener {
            //创建File对象，用于存储拍照后的图片
            outputImage = File(requireContext().externalCacheDir, "output_image.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri =
                FileProvider.getUriForFile(
                    requireContext(),
                    "com.application.traveldiary.fileprovider",
                    outputImage
                )
            //启动相机程序
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

            takePhotoLauncher.launch(intent)
        }

        binding.fromAlbumBtn.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            fromAlbumLauncher.launch(intent)

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            fromAlbumLauncher.launch(intent)
        }
    }
}