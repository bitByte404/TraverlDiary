package com.application.traveldiary.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.R
import com.application.traveldiary.adapter.DynamicAdapter
import com.application.traveldiary.databinding.FragmentCommunityBinding
import com.application.traveldiary.models.Dynamic
import com.application.traveldiary.utils.CommunityTest
import com.application.traveldiary.viewModel.CommunityViewModel
import com.application.traveldiary.views.customView.PaginationView

class CommunityFragment : Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    private lateinit var dynamics: ArrayList<Dynamic>
    private lateinit var lastSelectedBar: PaginationView

    // 获取ViewModel的实例
    private val viewModel: CommunityViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityBinding.inflate(inflater)
        initTestData() //TODO 测试数据，后面需要关闭
        initData()
        addTouchEvent()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //配置热门分类是默认分类
        binding.hotBar.setDefaultSort()
        lastSelectedBar = binding.hotBar
    }

    //添加点击事件
    private fun addTouchEvent() {
        paginationViewTouchEvent()
        addButtonTouchEvent()
    }

    private fun paginationViewTouchEvent() {
        val barList = arrayListOf(binding.hotBar, binding.primeBar, binding.latestBar, binding.mineBar)
        barList.forEach { bar ->
            bar.setOnClickListener {
                val selected = it as PaginationView
                if (selected == lastSelectedBar) {
                    return@setOnClickListener
                }
                selected.changeState(true)
                lastSelectedBar.changeState(false)
                lastSelectedBar = selected
            }
        }
    }

    private fun addButtonTouchEvent() {
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_community_to_createDynamicsFragment)
        }
    }



    //初始话数据
    private fun initData() {
        val adapter = DynamicAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        binding.recyclerView.adapter = adapter
        adapter.setData(dynamics)
    }


    //TODO 供测试使用
    private fun initTestData() {
        dynamics = arrayListOf(CommunityTest.getDynamic(0), CommunityTest.getDynamic(1))
    }
}