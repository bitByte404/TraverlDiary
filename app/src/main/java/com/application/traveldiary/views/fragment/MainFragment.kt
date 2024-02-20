package com.application.traveldiary.views.fragment

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.application.traveldiary.R
import com.application.traveldiary.adapter.ViewPagerAdapter
import com.application.traveldiary.databinding.FragmentAlbumBinding
import com.application.traveldiary.databinding.FragmentMainBinding

class MainFragment : Fragment()  {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fragments = listOf<Fragment>(JourneyFragment(),AlbumFragment(),CommunityFragment(),PersonFragment()) //,
        binding.viewpager.apply {
            adapter = ViewPagerAdapter(requireActivity() as AppCompatActivity,fragments)

            //取消滑动切换页面
            isUserInputEnabled = false

            // 设置ViewPager2的页面改变监听器
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.navView.menu.getItem(position).isChecked = true
                }
            })

            // 设置BottomNavigationView的选择监听器
            binding.navView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_journey -> this.currentItem = 0
                    R.id.navigation_album -> this.currentItem = 1
                    R.id.navigation_community -> this.currentItem = 2
                    R.id.navigation_person -> this.currentItem = 3
                }
                true
            }
        }
    }
}