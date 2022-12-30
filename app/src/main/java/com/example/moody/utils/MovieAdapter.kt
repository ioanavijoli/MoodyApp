package com.example.moody.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.moody.data.Movie
import com.example.moody.databinding.MovieItemBinding


class MovieAdapter(
    private val context: Context,
    private val moviesList: List<Movie>
) : PagerAdapter() {
    override fun getCount(): Int {
        return moviesList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: MovieItemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(context), container, false)
        val item = moviesList[position]
        val title = item.title
        val year = item.year
        val runtime = item.runtime
        val genres = item.genres
        val genresString = genres.joinToString(", ")
        val director = item.director
        val actors = item.actors
        val plot = item.plot
        val posterUrl = item.posterUrl
        val pos = position + 1

        binding.title.text = "$pos) $title"
        binding.year.text = "Year: $year"
        binding.runtime.text = "Runtime: $runtime"
        binding.genres.text = "Genres: $genresString"
        binding.director.text = "Director: $director"
        binding.actors.text = "Actors: $actors"
        binding.plot.text = "Plot: $plot"

        Glide.with(binding.image)
            .load(posterUrl)
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