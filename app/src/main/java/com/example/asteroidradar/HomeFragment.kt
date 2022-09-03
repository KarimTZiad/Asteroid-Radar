package com.example.asteroidradar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asteroidradar.databinding.FragmentHomeBinding
import com.example.asteroidradar.viewmodels.HomeFragmentViewModel
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        binding.viewModel = viewModel

        val adapter = AsteroidAdapter(AsteroidAdapter.DetailsClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))
        })
        binding.asteroidRecyclerView.adapter = adapter
        binding.asteroidRecyclerView.layoutManager = LinearLayoutManager(activity)

        viewModel.readAllAsteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.pictureOfDay.observe(viewLifecycleOwner, Observer {pic ->
            pic?.let {
                Picasso.get().load(pic.url).into(binding.imageOfTheDay)
                binding.imageOfTheDay.setOnClickListener {
                    Toast.makeText(requireContext(), pic.title, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}