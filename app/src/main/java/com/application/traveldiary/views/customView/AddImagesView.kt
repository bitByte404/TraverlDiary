package com.application.traveldiary.views.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.application.traveldiary.databinding.LayoutAddImagesViewBinding

class AddImagesView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var binding: LayoutAddImagesViewBinding
    init {
        binding = LayoutAddImagesViewBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
    }
}