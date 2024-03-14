package com.application.traveldiary.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.application.traveldiary.R
import com.application.traveldiary.databinding.FragmentPictureBinding
import com.application.traveldiary.viewModel.AlbumViewModel
import com.bumptech.glide.Glide


class PictureFragment : Fragment() {
    private lateinit var binding:FragmentPictureBinding
    private val mViewModel:AlbumViewModel by activityViewModels<AlbumViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPictureBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.photoview.apply {
            setPic(mViewModel.picture!!.uri)
        }
    }
}