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
        //TODO 测试数据，后面需要关闭
        initTestData()
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


    fun initTestData() {
        val testDynamics = arrayListOf<Dynamic>()
        val bitmap1 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_images1)
        val bitmap2 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_images2)
        val bitmap3 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_images3)
        val bitmap4 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_images4)
        val bitmap5 = BitmapFactory.decodeResource(requireContext().resources, R.drawable.demo_images5)

        val avatar = BitmapFactory.decodeResource(requireContext().resources, R.drawable.avater)
        val testData = Date(System.currentTimeMillis())

        val picture1 = Picture(bitmap1, testData, "")
        val picture2 = Picture(bitmap2, testData, "")
        val picture3 = Picture(bitmap3, testData, "")
        val picture4 = Picture(bitmap4, testData, "")
        val picture5 = Picture(bitmap5, testData, "")
        val pictures = arrayListOf(picture1, picture2, picture3, picture4, picture5)

        val user = User(0, "大伟", "123", "110", 0, avatar, "")

        val comment1 = Comment("1", "1", user, "今天真的太开心了哈哈哈哈哈哈", 10, testData, "")
        val comment2 = Comment("2", "2", user, "666", 10, testData, "")
        val comment3 = Comment("3", "3", user, "好美啊", 10, testData, "")
        val comment4 = Comment("4", "4", user, "我也想去看看", 10, testData, "")
        val comment5 = Comment("5", "5", user, "太棒了", 10, testData, "")
        val comments = arrayListOf(comment1, comment2, comment3, comment3, comment4, comment5)

        testDynamics.add(Dynamic(
            "0",
            "测试1",
            "这是一个测试",
            pictures,
            50,
            user,
            testData,
            "Huawei",
            comments
        ))

        dynamics = testDynamics
    }

}