package com.example.moody.feature.categories

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moody.MainActivity
import com.example.moody.R
import com.example.moody.data.Song
import com.example.moody.databinding.FragmentMusicBinding
import com.example.moody.extensions.BundleArgumentDelegate
import com.example.moody.extensions.withArguments
import com.example.moody.utils.HttpClient
import com.example.moody.utils.MusicAdapter
import retrofit2.Call
import retrofit2.Response

class MusicFragment : Fragment(R.layout.fragment_music) {
    private var drawable: Int = R.drawable.happy
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMusicBinding.bind(view)
        arguments.let {
            if (it != null) {
                displaySongs(binding, it.category)
            }
        }
        binding.backButton.setOnClickListener {
            (activity as MainActivity).goBack()
        }
        binding.travelIdeas.setOnClickListener {
            arguments?.let { it1 -> (activity as MainActivity).openTravelScreen(it1.category) }
        }

    }

    private fun getGenres(category: String): String {
        var genres = ""
        when (category) {
            "Happy" -> {
                genres = "Latin|Folk|Hip Hop|Electronic|Rap|Pop|Reggae|Rock"
                drawable = R.drawable.happy
            }
            "Sad" -> {
                genres = "Non Music|Blues|Classical|Jazz|Country"
                drawable = R.drawable.sad
            }
            "Tired" -> {
                genres = "Folk|Blues|Stage And Screen|Classical|Jazz|Country"
                drawable = R.drawable.tired
            }
            "Nostalgic" -> {
                genres = "Latin|Folk|Blues|Classical|Soul|Funk|World|Jazz|Country"
                drawable = R.drawable.nostalgic
            }
            "Enthusiastic" -> {
                genres = "Metal|Hip Hop|Electronic|Rap|Rock"
                drawable = R.drawable.excited
            }
        }
        return genres
    }

    @SuppressLint("SetTextI18n")
    private fun displaySongs(binding: FragmentMusicBinding, category: String) {

        val genres = getGenres(category)
        binding.mood.text = "Song ideas: $category "
        binding.mood.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)

        HttpClient.songService.getSongsByGenre(genres).enqueue(
            object : retrofit2.Callback<List<Song>> {
                override fun onResponse(
                    call: Call<List<Song>>,
                    response: Response<List<Song>>
                ) {
                    if (response.isSuccessful) {

                        binding.pager.adapter =
                            response.body()?.shuffled().let {
                                it?.let { it1 ->
                                    MusicAdapter(
                                        binding.root.context,
                                        it1
                                    )
                                }
                            }
                    }
                }

                override fun onFailure(call: Call<List<Song>>, t: Throwable) {

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
        fun newInstance(category: String) = MusicFragment().withArguments {
            it.category = category

        }
    }
}