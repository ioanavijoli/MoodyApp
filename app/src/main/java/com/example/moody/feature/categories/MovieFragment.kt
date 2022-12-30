package com.example.moody.feature.categories

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moody.utils.HttpClient
import com.example.moody.MainActivity
import com.example.moody.R
import com.example.moody.data.Movie
import com.example.moody.databinding.FragmentMoviesBinding
import com.example.moody.extensions.BundleArgumentDelegate
import com.example.moody.utils.MovieAdapter
import com.example.moody.extensions.withArguments
import retrofit2.Call
import retrofit2.Response

class MovieFragment : Fragment(R.layout.fragment_movies) {
    private var drawable: Int = R.drawable.happy
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMoviesBinding.bind(view)
        arguments.let {
            if (it != null) {
                displayMovies(binding, it.category)
            }
        }
        binding.backButton.setOnClickListener {
            (activity as MainActivity).goBack()
        }
        binding.musicRecommendation.setOnClickListener {
            arguments?.let { it1 -> (activity as MainActivity).openMusicScreen(it1.category) }
        }

    }

    private fun getGenres(category: String): String{
        var genres = ""
        when(category){
            "Happy" ->{
                genres = "Comedy|Sport|Adventure|Romance"
                drawable = R.drawable.happy
            }
            "Sad" -> {
                genres = "Drama|Romance|Music"
                drawable = R.drawable.sad
            }
            "Tired" -> {
                genres = "Musical|Comedy|Animation|Romance"
                drawable = R.drawable.tired
            }
            "Nostalgic" -> {
                genres = "Biography|History|Family|Film-Noir"
                drawable = R.drawable.nostalgic
            }
            "Enthusiastic" -> {
                genres = "Fantasy|Western|Mystery|Action|War|Crime|Thriller|Sci-Fi|Horror"
                drawable = R.drawable.excited
            }
        }
        return genres
    }

    @SuppressLint("SetTextI18n")
    private fun displayMovies(binding: FragmentMoviesBinding, category: String){

        val genres = getGenres(category)
        binding.mood.text = "Movie ideas: $category "
        binding.mood.setCompoundDrawablesWithIntrinsicBounds(0,0, drawable, 0)

        HttpClient.movieService.getMoviesByGenre(genres).enqueue(
            object : retrofit2.Callback<List<Movie>> {
                override fun onResponse(
                    call: Call<List<Movie>>,
                    response: Response<List<Movie>>
                ) {
                    if (response.isSuccessful) {

                        binding.pager.adapter =
                            response.body()?.shuffled().let {
                                it?.let { it1 ->
                                    MovieAdapter(
                                        binding.root.context,
                                        it1
                                    )
                                }
                            }
                    }
                }

                override fun onFailure(call: Call<List<Movie>>, t: Throwable) {

                    t.printStackTrace()
                    Toast.makeText(
                        context,
                        getString(R.string.error_message),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }

    companion object {
        private var Bundle.category by BundleArgumentDelegate.String("category")
        fun newInstance(category: String) = MovieFragment().withArguments {
            it.category = category

        }
    }
}