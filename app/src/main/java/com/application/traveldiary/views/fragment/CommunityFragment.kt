package com.application.traveldiary.views.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.R
import com.application.traveldiary.adapter.DynamicAdapter
import com.application.traveldiary.adapter.DynamicPictureAdapter
import com.application.traveldiary.databinding.FragmentCommunityBinding
import com.application.traveldiary.models.Dynamic
import com.application.traveldiary.utils.CommunityTest
import com.application.traveldiary.viewModel.CommunityViewModel
import com.application.traveldiary.views.customView.PaginationView

class CommunityFragment : Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    private lateinit var dynamics: ArrayList<Dynamic>
    private lateinit var lastSelectedBar: PaginationView
    val adapter: DynamicAdapter by lazy {
        DynamicAdapter().apply {
            setOnImageItemClickListener(object : DynamicPictureAdapter.onImageItemClickListener {

                override fun onItemClick(imageUri: Uri, imageView: ImageView) {
                    imageView.transitionName = "shared_element"
                    val extras = FragmentNavigatorExtras(imageView to "shared_element")
                    val action = MainFragmentDirections
                        .actionMainFragmentToPictureFullScreenFragment(imageUri)
                    findNavController().navigate(action, extras)
                }
            })
        }
    }

    // 获取ViewModel的实例
    private val viewModel: CommunityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityBinding.inflate(inflater)
        initData()
        addTouchEvent()

        binding.swipeFresh.setColorSchemeResources(R.color.colorA)
        binding.swipeFresh.setOnRefreshListener {
            refreshData()
        }

        return binding.root
    }

    private fun refreshData() {
        //viewModel.addDynamic(CommunityTest.getDynamic())
        adapter.setData(viewModel.dynamics.value!!)
        adapter.notifyDataSetChanged()
        binding.swipeFresh.isRefreshing = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //配置热门分类是默认分类
        binding.hotBar.setDefaultSort()
        lastSelectedBar = binding.hotBar
        observeData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    //添加点击事件
    private fun addTouchEvent() {
        paginationViewTouchEvent()
        addButtonTouchEvent()
    }

    private fun observeData() {
        viewModel.dynamics.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setDataAndRefresh(it)
            }
        }
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
            findNavController().navigate(R.id.action_mainFragment_to_createDynamicsFragment)
        }
    }



    //初始话数据
    private fun initData() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        binding.recyclerView.adapter = adapter
        dynamics = viewModel.loadDynamic()
        adapter.setData(dynamics)
    }
}