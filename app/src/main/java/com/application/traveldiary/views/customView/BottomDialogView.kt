package com.application.traveldiary.views.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.application.traveldiary.databinding.LayoutBottomDialogBinding

class BottomDialogView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private val binding: LayoutBottomDialogBinding
//    private var takePhotoCallback: () -> Unit = {}
//    private var fromAlbumCallback: () -> Unit = {}
//    private var cancelBtnCallback: () -> Unit = {}
    init {
        binding = LayoutBottomDialogBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
    }



    fun addTakePhotoCallback(callback: () -> Unit) {
        binding.takePhotoBtn.setOnClickListener {
            callback()
        }
    }

    fun addFromAlbumCallback(callback: () -> Unit) {
        binding.fromAlbumBtn.setOnClickListener {
            callback()
        }
    }

    fun cancelBtnCallback(callback: () -> Unit) {
        binding.cancelBtn.setOnClickListener {
            callback()
        }
    }
}