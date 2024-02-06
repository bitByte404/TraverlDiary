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
import com.application.traverldiary.databinding.FragmentCommunityBinding
import com.application.traverldiary.models.Comment
import com.application.traverldiary.models.Dynamic
import com.application.traverldiary.models.Picture
import com.application.traverldiary.models.User
import java.util.Date

class CommunityFragment : Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    private lateinit var dynamics: ArrayList<Dynamic>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCommunityBinding.inflate(inflater)
        initTestData() //TODO 测试数据，后面需要关闭
        initData()
        return binding.root
    }

    fun initData() {
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
    fun initTestData() {
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


        val avatar = BitmapFactory.decodeResource(requireContext().resources, R.drawable.avater)
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

        val user = User(0, "阿伟", "123", "110", 0, avatar, "")
        val user1 = User(0, "大橘子", "123", "110", 0, avatar2, "")
        val user2 = User(0, "小橙子", "123", "110", 0, avatar2, "")
        val user3 = User(0, "wuliner", "123", "110", 0, avatar, "")
        val user4 = User(0, "大孙", "123", "110", 0, avatar, "")
        val user5 = User(0, "阿成", "123", "110", 0, avatar, "")


        val comment1 = Comment("1", "1", user1, "今天真的太开心了哈哈哈哈哈哈", 10, testData, "")
        val comment2 = Comment("2", "2", user2, "666", 10, testData, "")
        val comment3 = Comment("3", "3", user3, "好美啊", 10, testData, "")
        val comment4 = Comment("4", "4", user4, "我也想去看看", 10, testData, "")
        val comment5 = Comment("5", "5", user5, "太棒了", 10, testData, "")

        val comments = arrayListOf(comment1, comment2, comment3, comment3, comment4, comment5)


        testDynamics.add(Dynamic(
            "0",
            "落日沉溺于橘色的海，晚风沦陷于赤诚的爱",
            "这是一个测试",
            pictures,
            50,
            user,
            testData,
            "Huawei",
            comments
        ))

        testDynamics.add(Dynamic(
            "1",
            "与其幻想，不如追寻",
            "这个一个测试",
            pictures2,
            34,
            user2,
            testData,
            "Apple",
            comments
        ))

        dynamics = testDynamics
    }

}