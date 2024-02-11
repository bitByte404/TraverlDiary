package com.application.traverldiary.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.traverldiary.R
import com.application.traverldiary.databinding.FragmentWelcomeBinding
import com.example.booting.ui.tool.AnimationTools
import com.example.booting.ui.tool.SPTools


class WelcomeFragment : Fragment() {
    lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        AnimationTools.addScaleAndAlphaAnimation(
            binding.logo,
            0.4f,1f,01f,1f,
            BounceInterpolator(),
            1500,
            onEnd = {
                navigate()
            }
        )
    }

    private fun navigate(){
        //判断是否是第一次使用
        if (SPTools.defaultTools(requireContext()).isFirst) {
            findNavController().navigate(R.id.action_welcomeFragment_to_guideFragment)
        }else{
            findNavController().
            navigate(R.id.action_welcomeFragment_to_navigation_journey)
        }
    }
}