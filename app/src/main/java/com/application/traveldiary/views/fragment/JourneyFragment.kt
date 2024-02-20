package com.application.traveldiary.views.fragment

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
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
                showPickedDate()
            }
        }

        /**
         * yesterday
         */
        binding.yesterday.setOnClickListener {
            switchToYesterday()
        }

        /**
         * tomorrow
         */
        binding.tomorrow.setOnClickListener {
            switchTomorrow()
        }

    }

    /**
     * 设置默认时间：当天
     */
    private fun setDefaultDate(dateView: TextView) {
        val todayDate = Calendar.getInstance(TimeZone.getTimeZone("CHINA"))
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormat.format(todayDate.time)
        dateView.text = formattedDate.toString()
    }

    /**
     * 跳转至指定时间
     */
    @SuppressLint("SetTextI18n")
    private fun showPickedDate() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.show(parentFragmentManager, picker.toString())

        picker.addOnPositiveButtonClickListener { selectedDate ->
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("CHINA"))
            calendar.time = Date(selectedDate)
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            Log.v("csh", month.toString())
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            Toast.makeText(
                requireContext(),
                "Selected date: $year-${month + 1}-$dayOfMonth",
                Toast.LENGTH_SHORT
            ).show()
            binding.datePicker.text = "$year-${month + 1}-$dayOfMonth"
        }
    }

    /**
     * 跳转到前一天
     */
    private fun switchToYesterday() {
        val selectedDate = binding.datePicker.text.toString()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.parse(selectedDate)

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("CHINA"))
        calendar.time = currentDate
        calendar.add(Calendar.DAY_OF_MONTH, -1) // 减去一天

        val yesterdayDate = dateFormat.format(calendar.time)
        binding.datePicker.text = yesterdayDate
    }

    /**
     * 跳转到后一天
     */
    private fun switchTomorrow() {
        val selectedDate = binding.datePicker.text.toString()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.parse(selectedDate)

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("CHINA"))
        calendar.time = currentDate
        calendar.add(Calendar.DAY_OF_MONTH, 1) // 加上一天

        val tomorrowDate = dateFormat.format(calendar.time)
        binding.datePicker.text = tomorrowDate
    }
}