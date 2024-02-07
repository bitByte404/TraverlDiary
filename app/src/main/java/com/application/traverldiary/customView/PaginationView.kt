package com.application.traverldiary.customView

import android.content.Context
import android.text.Spannable.Factory
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.application.traverldiary.R
import com.application.traverldiary.databinding.LayoutPaginationViewBinding

class PaginationView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var binding: LayoutPaginationViewBinding
    private var defaultHeightColor = ContextCompat.getColor(context, R.color.height_blue)
    private var heightColor: Int
    private var unslectedColor: Int = ContextCompat.getColor(context, R.color.unselected_color)

    init {
        binding = LayoutPaginationViewBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
        //从自定义属性中获取数据
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.PaginationView)
        heightColor = typeArray.getColor(R.styleable.PaginationView_highlight_color, defaultHeightColor)
        val sortName = typeArray.getString(R.styleable.PaginationView_sort_name) ?: "未设置"
        unslectedColor = typeArray.getColor(R.styleable.PaginationView_highlight_color, unslectedColor)
        typeArray.recycle()
//        //配置突出的颜色
//        binding.underline.setBackgroundColor(heightColor)
//        binding.sortName.setTextColor(heightColor)
        //设置分类名称
        binding.sortName.text = sortName

        changeState(sortName == "热门") //TODO 只是为了测试效果，后面需要修改
    }

    //根据是否选中，修改
    fun changeState(isSelected: Boolean) {
        if (isSelected) {
            binding.underline.visibility = View.VISIBLE
            binding.sortName.setTextColor(heightColor)
        } else {
            binding.underline.visibility = View.INVISIBLE
            binding.sortName.setTextColor(unslectedColor)
        }
    }
}