package com.application.traveldiary.views.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.application.traveldiary.databinding.FragmentCreateDynamicsBinding
import com.bumptech.glide.Glide
import java.io.File

class CreateDynamicsFragment : Fragment() {
    private lateinit var binding: FragmentCreateDynamicsBinding
    lateinit var imageUri: Uri
    lateinit var outputImage: File

    private val takePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Glide.with(this)
                    .load(imageUri)
                    .centerCrop()
                    .into(binding.imageView)
            }
        }

    private val fromAlbumLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                result.data?.data.let { uri ->
                    Glide.with(this)
                        .load(uri)
                        .centerCrop()
                        .into(binding.image)
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
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            fromAlbumLauncher.launch(intent)
        }
    }
}