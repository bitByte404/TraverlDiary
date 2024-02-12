package com.application.traveldiary.views.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.application.traveldiary.databinding.LayoutSearchViewBinding

//自定义搜索视图
class SearchView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var binding: LayoutSearchViewBinding
    init {
        binding = LayoutSearchViewBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
    }
}