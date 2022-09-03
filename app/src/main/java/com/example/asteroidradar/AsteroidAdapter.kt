package com.example.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.databinding.AsteroidRowItemBinding


class AsteroidAdapter(private val clickListener: DetailsClickListener) : ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidItemDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(clickListener, asteroid)
    }

    fun setData(asteroids:List<Asteroid>){
        submitList(asteroids)
    }

    class ViewHolder private constructor(private val binding: AsteroidRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: DetailsClickListener, asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.clickListener = clickListener
        }
        companion object{
            fun from(parent: ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidRowItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class AsteroidItemDiffCallback :
        DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }
    }

    class DetailsClickListener(val clickListener: (code: Long) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid.id)
    }
}
