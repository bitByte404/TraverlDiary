package com.application.traverldiary.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.traverldiary.R
import com.application.traverldiary.databinding.FragmentPersonBinding

class PersonFragment : Fragment() {
    private lateinit var binding: FragmentPersonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPersonBinding.inflate(inflater)
        return binding.root
    }

}