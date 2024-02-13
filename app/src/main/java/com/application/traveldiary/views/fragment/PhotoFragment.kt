package com.application.traveldiary.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.application.traveldiary.R
import com.application.traveldiary.databinding.FragmentImgBinding
import com.bumptech.glide.Glide
import io.reactivex.annotations.Nullable
import uk.co.senab.photoview.PhotoView
import uk.co.senab.photoview.PhotoViewAttacher


/**
 * Created by zheng on 2017/11/27.
 */
class PhotoFragment : Fragment() {
    private lateinit var binding:FragmentImgBinding
    private var url: String? = null
    private var mPhotoView: PhotoView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = requireArguments().getString("url")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImgBinding.inflate(LayoutInflater.from(context))
        mPhotoView = binding.photoview
        //设置缩放类型，默认ScaleType.CENTER（可以不设置）
        mPhotoView!!.scaleType = ImageView.ScaleType.CENTER
        mPhotoView!!.setOnLongClickListener(OnLongClickListener {
            true
        })
        mPhotoView!!.onPhotoTapListener =
            PhotoViewAttacher.OnPhotoTapListener { view, x, y -> }
        Glide.with(requireContext())
            .load(url)
            .placeholder(R.mipmap.ic_launcher) //加载过程中图片未显示时显示的本地图片
            .error(R.mipmap.ic_launcher) //加载异常时显示的图片
                                //  .centerCrop()//图片图填充ImageView设置的大小
            .fitCenter() //缩放图像测量出来等于或小于ImageView的边界范围,该图像将会完全显示
            .into(mPhotoView!!)
        return view
    }

    companion object {
        /**
         * 获取这个fragment需要展示图片的url
         * @param url
         * @return
         */
        fun newInstance(url: String?): PhotoFragment {
            val fragment = PhotoFragment()
            val args = Bundle()
            args.putString("url", url)
            fragment.arguments = args
            return fragment
        }
    }
}