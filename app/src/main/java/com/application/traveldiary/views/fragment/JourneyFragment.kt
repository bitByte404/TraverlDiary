package com.application.traveldiary.views.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.traveldiary.manager.JourneyLayoutManager
import com.application.traveldiary.adapter.JourneyAdapter
import com.application.traveldiary.adapter.TimelineAdapter
import com.application.traveldiary.databinding.FragmentJourneyBinding
import com.application.traveldiary.utils.journeyCreator
import com.application.traveldiary.utils.timeCreator


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
        val tLayoutManger = LinearLayoutManager(requireContext())

        binding.timeAxisRecyclerView.apply {
            layoutManager = tLayoutManger
            adapter = TimelineAdapter(timeCreator())
        }

        val jLayoutManger = JourneyLayoutManager(requireContext(), journeyCreator())
        binding.tasksRecyclerView.apply {
            layoutManager = jLayoutManger
            adapter = JourneyAdapter(journeyCreator())
        }
    }
}