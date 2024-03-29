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
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.adapter.DynamicPictureAdapter
import com.application.traveldiary.databinding.FragmentCreateDynamicsBinding
import com.application.traveldiary.models.Dynamic
import com.application.traveldiary.viewModel.CommunityViewModel
import com.application.traveldiary.views.customView.BottomDialogView
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.util.Date

class CreateDynamicsFragment : Fragment() {
    private lateinit var binding: FragmentCreateDynamicsBinding
    private lateinit var imageUri: Uri // 图片的Uri
    private lateinit var outputImage: File // 输出的图片文件
    val uriList = arrayListOf<Uri>()
    private lateinit var pictureAdapter: DynamicPictureAdapter
    val viewModel: CommunityViewModel by activityViewModels()


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
                    takePersistableUriPermission(uri)
                    uriList.add(uri)
                }
            }
            pictureAdapter.setData(uriList)
        }
    }

    // 获取持球访问权限
    private fun takePersistableUriPermission(uri: Uri) {
        val persistedUris = requireActivity().contentResolver.persistedUriPermissions
        if (persistedUris.any { it.uri == uri }) {
            // 先检测是否已经申请过权限，避免重复申请后报错
            return
        }
        val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        requireActivity().contentResolver.takePersistableUriPermission(uri, takeFlags)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
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

    override fun onResume() {
        super.onResume()
        pictureAdapter.setData(uriList)
    }

    //初始化数据
    private fun initData() {
        //配置动态的图片
        pictureAdapter = DynamicPictureAdapter().apply {

            setOnImageItemClickListener(object : DynamicPictureAdapter.onImageItemClickListener {
                override fun onItemClick(imageUri: Uri, imageView: ImageView) {
                    imageView.transitionName = "shared_element"
                    val extras = FragmentNavigatorExtras(imageView to "shared_element")
                    val action = CreateDynamicsFragmentDirections.
                    actionCreateDynamicsFragmentToPictureFullScreenFragment2(imageUri)
                    findNavController().navigate(action, extras)
                }
            })
        }
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

            // TODO
//            val dynamic = viewModel.uploadAndGetDynamic(
//                uriList,
//                title,
//                content,
//                onStart = {
//                    Toast.makeText(requireContext(), "正在上传", Toast.LENGTH_SHORT).show()
//                    findNavController().navigateUp()
//                },
//                onEnd = {
//                    Toast.makeText(requireContext(), "上传成功", Toast.LENGTH_SHORT).show()
//                }
//            )


                val dynamic = Dynamic("", title, content, urisToString(), 0, viewModel.currentUser.value!!, Date(), comments = arrayListOf())
                viewModel.addDynamicAndAddToFile(dynamic)
                findNavController().navigateUp()
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