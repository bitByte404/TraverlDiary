package com.application.traverldiary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.traverldiary.R
import com.application.traverldiary.databinding.FragmentJourneyBinding

class JourneyFragment : Fragment() {
    private lateinit var binding: FragmentJourneyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentJourneyBinding.inflate(inflater)
        return binding.root
    }

}