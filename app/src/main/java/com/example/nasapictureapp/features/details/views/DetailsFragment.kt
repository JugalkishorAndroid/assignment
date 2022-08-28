package com.example.nasapictureapp.features.details.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.nasapictureapp.R
import com.example.nasapictureapp.core.BaseFragment
import com.example.nasapictureapp.databinding.FragmentDetailsBinding
import com.example.nasapictureapp.databinding.FragmentGridBinding
import com.example.nasapictureapp.features.details.util.adapter.ViewRecyclerAdapter
import com.example.nasapictureapp.features.picture.viewmodel.ImageSharedViewModel

class DetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val imageSharedViewModel: ImageSharedViewModel by navGraphViewModels(
        R.id.app_navigation
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        handleBackPressed()
    }

    private fun setObserver() {
        imageSharedViewModel.sharedImageItem.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.viewpager.adapter = ViewRecyclerAdapter(it)
                binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                binding.viewpager.currentItem = imageSharedViewModel.getSelectedImagePosition()
            }
        }
    }

    private fun handleBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            onBackPressed()
        }
    }

    private fun onBackPressed() {
        findNavController().popBackStack()
    }
}