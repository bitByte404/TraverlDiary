package com.application.traverldiary.ui.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.application.traverldiary.databinding.FragmentGuideBinding


class GuideFragment : Fragment() {
    private lateinit var binding: FragmentGuideBinding
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuideBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //写入初次登陆的数据
        //SPTools.defaultTools(requireContext()).isFirst = false

        //配置viewPager的Adapter
        binding.viewpager.adapter = GuideAdapter(
            listOf(FirstFragment(),SecondFragment()),
            parentFragmentManager,
            lifecycle
        )
        binding.viewpager.registerOnPageChangeCallback(object :OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.indicatorView.select(position)
            }
        })
    }
}