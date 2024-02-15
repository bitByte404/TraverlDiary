package com.application.traveldiary.views.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.IntentCompat.getParcelableExtra
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.application.traveldiary.R
import com.application.traveldiary.databinding.FragmentPictureBinding
import com.application.traveldiary.viewModel.AlbumViewModel
import com.bumptech.glide.Glide
import uk.co.senab.photoview.PhotoView
import java.util.function.LongFunction


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
            Glide.with(requireContext())
                .load(mViewModel.picture!!.uri)
                .fitCenter()
                .error(R.drawable.icon_heart)
                .into(this)
        }
    }
}