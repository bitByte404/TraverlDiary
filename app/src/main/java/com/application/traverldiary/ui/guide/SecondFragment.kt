package com.application.traverldiary.ui.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.traverldiary.R
import com.application.traverldiary.databinding.FragmentSecondBinding
import com.example.booting.ui.tool.AnimationTools
import com.example.booting.ui.tool.SPTools

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater,container,false)
        binding.btExperience.setOnClickListener{
            //改变isFirst
            SPTools.defaultTools(requireContext()).isFirst = false
            //切换到主页
            findNavController().navigate(R.id.action_guideFragment_to_navigation_journey)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        AnimationTools.addRotateAndAlphaAnimation(binding.tvTitle, onEnd = {})
        AnimationTools.addRotateAndAlphaAnimation(binding.tvDesc, onEnd = {})
    }
}