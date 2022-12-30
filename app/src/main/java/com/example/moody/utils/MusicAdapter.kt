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
import com.example.moody.data.Song
import com.example.moody.databinding.MovieItemBinding
import com.example.moody.databinding.MusicItemBinding


class MusicAdapter(
    private val context: Context,
    private val songsList: List<Song>
) : PagerAdapter() {
    override fun getCount(): Int {
        return songsList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: MusicItemBinding =
            MusicItemBinding.inflate(LayoutInflater.from(context), container, false)
        val item = songsList[position]
        val title = item.title
        val year = item.year
        val artist = item.artist
        val genre = item.genre
        val image = item.image
        val pos = position + 1

        binding.title.text = "$pos) $title"
        binding.year.text = "Year: $year"
        binding.artist.text = "Artist: $artist"
        binding.genre.text = "Genre: $genre"


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