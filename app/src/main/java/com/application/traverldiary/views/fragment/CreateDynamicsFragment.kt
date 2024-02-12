package com.application.traverldiary.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.traverldiary.databinding.FragmentCommunityBinding
import com.application.traverldiary.databinding.FragmentCreateDynamicsBinding

class CreateDynamicsFragment : Fragment() {
    private lateinit var binding: FragmentCreateDynamicsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateDynamicsBinding.inflate(inflater)
        return binding.root
    }
}