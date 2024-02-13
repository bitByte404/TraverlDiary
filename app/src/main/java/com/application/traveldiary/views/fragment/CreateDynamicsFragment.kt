package com.application.traveldiary.views.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.R
import com.application.traveldiary.adapter.DynamicPictureAdapter
import com.application.traveldiary.databinding.FragmentCreateDynamicsBinding
import com.application.traveldiary.databinding.LayoutAddImagesViewBinding
import com.application.traveldiary.models.Dynamic
import com.application.traveldiary.utils.CommunityTest
import com.application.traveldiary.utils.DateUtils
import com.application.traveldiary.utils.ImageUploader
import com.application.traveldiary.views.customView.BottomDialogView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date

class CreateDynamicsFragment : Fragment() {
    private lateinit var binding: FragmentCreateDynamicsBinding
    private lateinit var imageUri: Uri // 图片的Uri
    private lateinit var outputImage: File // 输出的图片文件
    val uriList = arrayListOf<Uri>()
    private lateinit var pictureAdapter: DynamicPictureAdapter

    // 初测一个启动活动的结果回调，用于处理拍照后的结果
    private val takePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                uriList.add(imageUri)
                pictureAdapter.setData(uriList)
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
                    uriList.add(uri)
                }
            } else {
                result.data?.data?.let { uri ->
                    uriList.add(uri)
                }
            }
            pictureAdapter.setData(uriList)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateDynamicsBinding.inflate(inflater)
        touchEvent()
        initData()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addTextListener()
        super.onViewCreated(view, savedInstanceState)
    }

    //初始化数据
    private fun initData() {
        //配置动态的图片
        pictureAdapter = DynamicPictureAdapter()
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 3)
        binding.recyclerView.adapter = pictureAdapter
    }

    //处理触摸事件
    private fun touchEvent() {
        binding.addView.setOnClickListener {
            addViewClicked()
        }
        binding.cancelText.setOnClickListener {
            findNavController().navigateUp()
        }
        publishBtnClicked()
    }

    private fun publishBtnClicked() {
        // TODO
        binding.publishButton.setOnClickListener {
            val title = binding.title.text.toString()
            val content = binding.content.text.toString()
            val list = arrayListOf<String>()
            lifecycleScope.launch {
                val imageUploader = ImageUploader()
                imageUploader.setSuccessCallback {
                    list.add(it)
                }
                imageUploader.setOnStartCallback {

                }
                uriList.forEach {
                    imageUploader.uploadByUri(it)
                }
                val dynamic = Dynamic("", title, content, list, 0, CommunityTest.getUser(), Date(), comments = arrayListOf())
            }
        }
    }

    private fun addViewClicked() {
        val bottomSheetDialog = BottomSheetDialog(
            requireContext()
        )
        val contentView = BottomDialogView(requireContext(), attrs = null)
        bottomSheetDialog.setContentView(contentView)
        bottomSheetDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // 给从相册选择按钮添加点击事件回调
        contentView.addFromAlbumCallback {
            getPhotoFromAlbum()
            bottomSheetDialog.dismiss()
        }
        // 给拍摄按钮添加点击事件回调
        contentView.addTakePhotoCallback {
            takePhoto()
            bottomSheetDialog.dismiss()
        }
        // 给取消按钮添加点击事件回调
        contentView.cancelBtnCallback {
            bottomSheetDialog.dismiss()
        }
        // 展示底部弹窗
        bottomSheetDialog.show()
    }


    //从相册获得图片
    private fun getPhotoFromAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        fromAlbumLauncher.launch(intent)
    }

    //拍照获得图片
    private fun takePhoto() {
        //创建File对象，用于存储拍照后的图片
        outputImage = File(requireContext().externalCacheDir, "output_image.jpg")
        if (outputImage.exists()) {
            outputImage.delete()
        }
        outputImage.createNewFile()
        this.imageUri =
            FileProvider.getUriForFile(
                requireContext(),
                "com.application.traveldiary.fileprovider",
                outputImage
            )
        //启动相机程序
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, this.imageUri)
        takePhotoLauncher.launch(intent)
    }

    //添加输入文本监听
    private fun addTextListener() {
        binding.title.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        checkPublish()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}})
        binding.content.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            checkPublish()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
})

    pictureAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
        super.onChanged()
        checkPublish()
            }
        })
    }

// 更新 checkPublish 方法
    fun checkPublish() {
        binding.publishButton.isEnabled = binding.title.text.toString().isNotEmpty() &&
                binding.content.text.toString().isNotEmpty() &&
                uriList.isNotEmpty()
    }

    fun urisToString(): List<String> {
        val temp = arrayListOf<String>()
        uriList.forEach {
            temp.add(it.toString())
        }
        return temp
    }
}