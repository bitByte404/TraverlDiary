package com.application.traverldiary.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.traverldiary.TimelineAdapter
import com.application.traverldiary.databinding.FragmentJourneyBinding
import com.application.traverldiary.tools.Tools


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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val jLayoutManger = LinearLayoutManager(requireContext())

        binding.journeyRecyclerView.apply {
            layoutManager = jLayoutManger
            adapter = TimelineAdapter(Tools.timeCreator())
        }


    }
}