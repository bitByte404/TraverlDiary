package com.application.traverldiary.ui.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.traverldiary.databinding.FragmentFristBinding
import com.example.booting.ui.tool.AnimationTools


class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFristBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFristBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        AnimationTools.addRotateAndAlphaAnimation(binding.tvTitle, onEnd = {})
        AnimationTools.addRotateAndAlphaAnimation(binding.tvDesc, onEnd = {})
    }
}