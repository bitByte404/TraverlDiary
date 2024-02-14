package com.application.traveldiary.guide_part.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.traveldiary.R
import com.application.traveldiary.databinding.FragmentSecondBinding
import com.application.traveldiary.guide_part.tool.AnimationTools
import com.application.traveldiary.guide_part.tool.SPTools
import com.google.android.material.bottomnavigation.BottomNavigationView

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
            findNavController().navigate(R.id.action_guideFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        AnimationTools.addRotateAndAlphaAnimation(binding.tvTitle, onEnd = {})
        AnimationTools.addRotateAndAlphaAnimation(binding.tvDesc, onEnd = {})
    }
}