package com.example.asteroidradar

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asteroidradar.databinding.FragmentHomeBinding
import com.example.asteroidradar.viewmodels.HomeFragmentViewModel
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel : HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        when(item.itemId){
            R.id.viewWeekAsteroidsMenuItem -> {
                viewModel.getWeekAsteroids()
            }

            R.id.viewTodayAsteroidsMenuItem -> {
                viewModel.getTodayAsteroids()
            }

            R.id.viewSavedAsteroidsMenuItem -> {
                viewModel.getAllAsteroids()
            }

            android.R.id.home ->
                navController.navigateUp()
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        binding.viewModel = viewModel

        val adapter = AsteroidAdapter(AsteroidAdapter.DetailsClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))
        })
        binding.asteroidRecyclerView.adapter = adapter
        binding.asteroidRecyclerView.layoutManager = LinearLayoutManager(activity)

        viewModel.currentlySelectedAsteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.pictureOfDay.observe(viewLifecycleOwner, Observer { pic ->
            Picasso.get()
                .load(pic?.url)
                .placeholder(R.drawable.placeholder_image_of_day)
                .error(R.drawable.ic_baseline_error_outline_128)
                .into(binding.imageOfTheDay)
            binding.imageOfTheDay.setOnClickListener {
                    Toast.makeText(requireContext(), pic?.title ?: requireContext().getString(R.string.no_image_of_the_day), Toast.LENGTH_LONG).show()
            }
        })
    }

}