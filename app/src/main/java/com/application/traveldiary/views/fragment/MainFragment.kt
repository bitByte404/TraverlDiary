package com.application.traveldiary.views.fragment

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.application.traveldiary.PersonFragment
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
        val fragments = listOf<Fragment>(JourneyFragment(),AlbumFragment(),CommunityFragment(),
            PersonFragment()
        ) //,
        binding.viewpager.apply {
            adapter = ViewPagerAdapter(requireActivity() as AppCompatActivity,fragments)

            //取消滑动切换页面
            isUserInputEnabled = false

            // 设置ViewPager2的页面改变监听器
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                }
            })

            // 设置BottomNavigationView的选择监听器
            binding.navView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_journey -> setCurrentItem(0,false)
                    R.id.navigation_album -> setCurrentItem(1,false)
                    R.id.navigation_community -> setCurrentItem(2,false)
                    R.id.navigation_person -> setCurrentItem(3,false)
                }
                true
            }
        }
    }
}