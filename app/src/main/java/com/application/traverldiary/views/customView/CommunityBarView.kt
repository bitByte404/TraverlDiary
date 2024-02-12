package com.application.traverldiary.views.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.application.traverldiary.databinding.LayoutCommunityBarViewBinding

class CommunityBarView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var binding: LayoutCommunityBarViewBinding
    init {
        binding = LayoutCommunityBarViewBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
    }
}