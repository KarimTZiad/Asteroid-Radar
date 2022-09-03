package com.example.asteroidradar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.asteroidradar.databinding.FragmentDetailBinding
import com.example.asteroidradar.viewmodels.DetailsFragmentViewModel
import com.example.asteroidradar.viewmodels.DetailsFragmentViewModelFactory

class DetailFragment : Fragment() {

    private lateinit var binding : FragmentDetailBinding
    private val args : DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = DetailsFragmentViewModelFactory(requireActivity().application, args.asteroidId)
        val viewModel = ViewModelProvider(this, viewModelFactory)[DetailsFragmentViewModel::class.java]
        viewModel.asteroid.observe(viewLifecycleOwner, Observer {
            it?.let{
                binding.asteroid = it
                binding.executePendingBindings()
            }
        })
    }

}