package com.example.moody.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.moody.data.City
import com.example.moody.databinding.CityItemBinding


class CityAdapter(
    private val context: Context,
    private val citiesList: List<City>
) : PagerAdapter() {
    override fun getCount(): Int {
        return citiesList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: CityItemBinding =
            CityItemBinding.inflate(LayoutInflater.from(context), container, false)
        val item = citiesList[position]
        val name = item.name
        val country = item.country
        val population = item.inhabitants
        val attractions = item.tourist_attractions
        val attractionsString = attractions.joinToString(", ")
        val restaurants = item.restaurants
        val restaurantsString = restaurants.joinToString(", ")
        val image = item.image
        val pos = position + 1

        binding.name.text = "$pos) $name"
        binding.country.text = "Country: $country"
        binding.population.text = "Population: $population inhabitants"
        binding.attractions.text = "Tourist attractions: $attractionsString"
        binding.restaurants.text = "Restaurants: $restaurantsString"

        Glide.with(binding.image)
            .load(image)
            .into(binding.image)

        container.addView(binding.root)

        return binding.root
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}