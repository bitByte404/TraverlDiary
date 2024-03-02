package com.application.traveldiary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.traveldiary.databinding.FragmentPersonBinding


class PersonFragment : Fragment() {

    private lateinit var binding: FragmentPersonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPersonBinding.inflate(inflater,null,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var profile =binding.shapeableImageView
        var name=binding.wangming
        var trip =binding.tripRecord
        var trends = binding.trendsRecord
        var concernNum =0
        binding.button.setOnClickListener {
            var text =binding.button.text
            if (text=="＋ 关注"){
                text="√ 已关注"
                concernNum++
            }else{
                text="＋ 已关注"
                concernNum--
            }
        }

        profile.setOnClickListener {
            change()
        }

        name.setOnClickListener {
            change()
        }

        trip.setOnClickListener{
            //展开行程史
            tripAll()
        }
        trends.setOnClickListener {
            //展开动态
            trendsAll()
        }
    }

    private fun change(){
        //弹出底部弹窗
        //修改
        //changeProfile() or changeName()
        //取消
    }
    //修改图像
    fun changeProfile(){}
    //修改网名
    fun changeName(){}
    //展开行程史
    private fun tripAll(){}
    //展开动态
    private fun trendsAll(){}

}