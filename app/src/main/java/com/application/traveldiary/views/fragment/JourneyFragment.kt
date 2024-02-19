package com.application.traveldiary.views.fragment

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.adapter.JourneysAdapter
import com.application.traveldiary.adapter.TimelineAdapter
import com.application.traveldiary.databinding.FragmentJourneyBinding
import com.application.traveldiary.utils.journeyCreator
import com.application.traveldiary.utils.timeCreator
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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

        /**
         * timeAxisRecyclerView
         */
        val tLayoutManger = LinearLayoutManager(requireContext())
        binding.timeAxisRecyclerView.apply {
            layoutManager = tLayoutManger
            adapter = TimelineAdapter(timeCreator())
        }

        /**
         * journeysRecyclerVIew
         */
        val tsLayoutManger =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.journeysRecyclerView.apply {
            layoutManager = tsLayoutManger
            adapter = JourneysAdapter(journeyCreator())
            // 使用 PagerSnapHelper 辅助实现整页滑动效果
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(this)
        }

        /**
         * datePicker
         * 日期选择器
         */
        binding.datePicker.apply {
            setDefaultDate(this)
            setOnClickListener {
                showDatePicker()
            }
        }

    }

    /**
     * 设置默认时间：当天
     */
    private fun setDefaultDate(dateView: TextView) {
        val today = Calendar.getInstance(TimeZone.getTimeZone("CHINA"))
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormat.format(today.time)
        dateView.text = formattedDate.toString()
    }

    /**
     * 跳转至指定时间
     */
    @SuppressLint("SetTextI18n")
    private fun showDatePicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.show(parentFragmentManager, picker.toString())

        picker.addOnPositiveButtonClickListener { selectedDate ->
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("CHINA"))
            calendar.time = Date(selectedDate)
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            Toast.makeText(
                requireContext(),
                "Selected date: $year-${month + 1}-$dayOfMonth",
                Toast.LENGTH_SHORT
            ).show()
            binding.datePicker.text = "$year-${month + 1}-$dayOfMonth"
        }
    }
}