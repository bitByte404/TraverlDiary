package com.application.traveldiary.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.application.traveldiary.databinding.FragmentPictureFullScreenBinding
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView

class PictureFullScreenFragment : Fragment() {
    private val args: PictureFullScreenFragmentArgs by navArgs()
    private lateinit var binding: FragmentPictureFullScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPictureFullScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUri = args.imageUri
        val photoView = binding.image
        Glide.with(requireContext())
            .load(imageUri)
            .into(photoView)
        binding.root.setOnClickListener {
            //findNavController().navigateUp()
        }
    }
}