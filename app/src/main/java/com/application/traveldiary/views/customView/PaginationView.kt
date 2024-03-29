package com.application.traveldiary.views.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.application.traveldiary.R
import com.application.traveldiary.databinding.LayoutPaginationViewBinding

//自定义动态分类视图
class PaginationView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var binding: LayoutPaginationViewBinding
    private var defaultHeightColor = ContextCompat.getColor(context, R.color.height_blue)
    private var heightColor: Int
    private var unselectedColor: Int = ContextCompat.getColor(context, R.color.unselected_color)

    init {
        binding = LayoutPaginationViewBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
        //从自定义属性中获取数据
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.PaginationView)
        heightColor = typeArray.getColor(
            R.styleable.PaginationView_highlight_color,
            defaultHeightColor
        )
        val sortName = typeArray.getString(R.styleable.PaginationView_sort_name) ?: "未设置"
        unselectedColor = typeArray.getColor(
            R.styleable.PaginationView_highlight_color,
            unselectedColor
        )
        typeArray.recycle()
        //设置分类名称
        binding.sortName.text = sortName
        changeState(false)
    }

    //根据是否选中，修改
    fun changeState(isSelected: Boolean) {
        if (isSelected) {
            binding.underline.visibility = View.VISIBLE
            binding.sortName.setTextColor(heightColor)
            binding.underline.setBackgroundColor(heightColor)
        } else {
            binding.underline.visibility = View.INVISIBLE
            binding.sortName.setTextColor(unselectedColor)
        }
    }

    //设置是否是默认分类
    fun setDefaultSort() {
        changeState(true)
    }
}