package com.application.traverldiary.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.traverldiary.R
import com.application.traverldiary.adapter.DynamicAdapter
import com.application.traverldiary.customView.PaginationView
import com.application.traverldiary.databinding.FragmentCommunityBinding
import com.application.traverldiary.models.Comment
import com.application.traverldiary.models.Dynamic
import com.application.traverldiary.models.Picture
import com.application.traverldiary.models.User
import java.util.Date

class CommunityFragment : Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    private lateinit var dynamics: ArrayList<Dynamic>
    private lateinit var lastSelectedBar: PaginationView
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
        addPaginationViewTouchEvent()
    }

    private fun addPaginationViewTouchEvent() {
        val barList = arrayListOf(binding.hotBar, binding.primeBar, binding.latestBar)
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
        val testDynamics = arrayListOf<Dynamic>()
        val bitmap1 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_pic1)
        val bitmap2 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_pic2)
        val bitmap3 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_pic3)
        val bitmap4 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_pic4)
        val bitmap5 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_pic5)
        val bitmap6 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_pic6)
        val bitmap7 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_pic7)
        val bitmap8 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_pic8)
        val bitmap9 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_pic9)
        val bitmap10 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_pic10)


        val avatar = BitmapFactory.decodeResource(requireContext().resources, R.drawable.avatar1)
        val avatar2 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.avatar2)

        val testData = Date(System.currentTimeMillis())

        val picture1 = Picture(bitmap1, testData, "")
        val picture2 = Picture(bitmap2, testData, "")
        val picture3 = Picture(bitmap3, testData, "")
        val picture4 = Picture(bitmap4, testData, "")
        val picture5 = Picture(bitmap5, testData, "")
        val picture6 = Picture(bitmap6, testData, "")
        val picture7 = Picture(bitmap7, testData, "")
        val picture8 = Picture(bitmap8, testData, "")
        val picture9 = Picture(bitmap9, testData, "")
        val picture10 = Picture(bitmap10, testData, "")

        val pictures = arrayListOf(picture1, picture2, picture3, picture4, picture5)
        val pictures2 = arrayListOf(picture6, picture7, picture8, picture9, picture10)

        val users = listOf("阿伟", "小橙子", "大橘子", "wuliner", "大孙", "阿成", "小香", "白白", "沸羊羊", "旺旺").mapIndexed { index, name ->
            User(index, name, "123", "110", 0, if (index % 2 == 0) avatar else avatar2, "")
        }

        val commentsContent = listOf("我简直无法用语言来形容。",
            "我真的感到非常的幸福和满足。",
            "这张图片让我想起了我曾经去过的一个地方",
            "它让我感到非常的平静和放松。",
            "这张图片真的很有艺术感",
            "这张图片让我想起了一首诗",
            "我真的很羡慕摄影师",
            "这张图片让我想起了我的家乡",
            "它让我感到非常的舒适和愉快。",
            "我可以感受到对于美景的热爱和敬仰。")

        val comments1 = arrayListOf<Comment>()
        val comments2 = arrayListOf<Comment>()
        commentsContent.mapIndexed { index, content ->
            val comment = Comment((index + 1).toString(), (index + 1).toString(), users[index], content, 10, testData, "")
            if (index < commentsContent.size / 2) {
                comments1.add(comment)
            } else {
                comments2.add(comment)
            }
        }



        testDynamics.add(Dynamic(
            "0",
            "落日沉溺于橘色的海，晚风沦陷于赤诚的爱",
            "这是一个测试",
            pictures,
            50,
            users[0],
            testData,
            "Huawei P60 Pro",
            comments1
        ))

        testDynamics.add(Dynamic(
            "1",
            "与其幻想，不如追寻",
            "这个一个测试",
            pictures2,
            34,
            users[1],
            testData,
            "iPhone15 Pro Max",
            comments2
        ))

        dynamics = testDynamics
    }



}