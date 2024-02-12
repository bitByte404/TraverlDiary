package com.application.traveldiary.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.R
import com.application.traveldiary.adapter.DynamicAdapter
import com.application.traveldiary.views.customView.PaginationView
import com.application.traveldiary.databinding.FragmentCommunityBinding
import com.application.traveldiary.databinding.LayoutCommunityBarViewBinding
import com.application.traveldiary.models.Comment
import com.application.traveldiary.models.Dynamic
import com.application.traveldiary.models.Picture
import com.application.traveldiary.models.User
import java.util.Date

class CommunityFragment : Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    private lateinit var dynamics: ArrayList<Dynamic>
    private lateinit var lastSelectedBar: PaginationView
    private lateinit var barbinding: LayoutCommunityBarViewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityBinding.inflate(inflater)
        barbinding = LayoutCommunityBarViewBinding.inflate(inflater)
        initTestData() //TODO 测试数据，后面需要关闭
        initData()
        addTouchEvent()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //配置热门分类是默认分类
        barbinding.hotBar.setDefaultSort()
        lastSelectedBar = barbinding.hotBar
    }

    //添加点击事件
    private fun addTouchEvent() {
        paginationViewTouchEvent()
        addButtonTouchEvent()
    }

    private fun paginationViewTouchEvent() {
        val barList = arrayListOf(barbinding.hotBar, barbinding.primeBar, barbinding.latestBar)
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
        val testDynamics = arrayListOf<Dynamic>()

        val pictureList = listOf(
            "https://imgs.wuliner.cn/imgs/demo_pic1.jpg",
            "https://imgs.wuliner.cn/imgs/demo_pic2.jpg",
            "https://imgs.wuliner.cn/imgs/demo_pic3.jpg",
            "https://imgs.wuliner.cn/imgs/demo_pic4.jpg",
            "https://imgs.wuliner.cn/imgs/demo_pic5.png",
            "https://imgs.wuliner.cn/imgs/demo_pic6.jpg",
            "https://imgs.wuliner.cn/imgs/demo_pic7.jpg",
            "https://imgs.wuliner.cn/imgs/demo_pic8.jpg",
            "https://imgs.wuliner.cn/imgs/demo_pic9.png",
            "https://imgs.wuliner.cn/imgs/demo_pic10.jpg"
        )


        val avatar1 = "https://imgs.wuliner.cn/imgs/avatar1.jpg"
        val avatar2 = "https://imgs.wuliner.cn/imgs/avatar2.jpg"

        val testData = Date(System.currentTimeMillis())

        val picture1 = Picture(pictureList[0], testData, "")
        val picture2 = Picture(pictureList[1], testData, "")
        val picture3 = Picture(pictureList[2], testData, "")
        val picture4 = Picture(pictureList[3], testData, "")
        val picture5 = Picture(pictureList[4], testData, "")
        val picture6 = Picture(pictureList[5], testData, "")
        val picture7 = Picture(pictureList[6], testData, "")
        val picture8 = Picture(pictureList[7], testData, "")
        val picture9 = Picture(pictureList[8], testData, "")
        val picture10 = Picture(pictureList[9], testData, "")


        val pictures = arrayListOf(picture1, picture2, picture3, picture4, picture5)
        val pictures2 = arrayListOf(picture6, picture7, picture8, picture9, picture10)

        val users = listOf("阿伟", "小橙子", "大橘子", "wuliner", "大孙", "阿成", "小香", "白白", "沸羊羊", "旺旺").mapIndexed { index, name ->
            User(index, name, "123", "110", 0, if (index % 2 == 0) avatar1 else avatar2, "")
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
            "落日沉溺于橘色的海，\n晚风沦陷于赤诚的爱。",
            "我在黄昏写上一封书信，载着落日的余晖和银河的浪漫。 寄给你，寄给温柔本身。",
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
            "白云恋我一往情深，蓝天赐我无限盼望，选我所爱，爱我所选。",
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