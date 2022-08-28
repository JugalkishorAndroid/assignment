package com.example.nasapictureapp.features.picture.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nasapictureapp.core.BaseFragment
import com.example.nasapictureapp.databinding.FragmentGridBinding
import com.example.nasapictureapp.features.picture.util.adapter.GridImageAdapter
import com.example.nasapictureapp.features.picture.viewmodel.GridViewModel
import com.example.nasapictureapp.util.OnClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class GridFragment : BaseFragment(), OnClickListener {


    private val viewModel: GridViewModel by viewModels()
    private lateinit var binding: FragmentGridBinding
    var gridImageAdapter: GridImageAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGridBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setObserver()
    }

    private fun setupRecyclerView() {
        gridImageAdapter = GridImageAdapter(this)
        binding.rcvImage.apply {
            adapter = gridImageAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun setObserver() {

        viewModel.imageSources.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                gridImageAdapter?.differ?.submitList(it)
            }
        }
    }

    override fun onClickItem(v: View, item: Any) {
        Timber.e("item $item")
    }
}