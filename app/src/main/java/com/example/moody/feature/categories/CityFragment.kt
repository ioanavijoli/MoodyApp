package com.example.moody.feature.categories

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moody.utils.HttpClient
import com.example.moody.MainActivity
import com.example.moody.R
import com.example.moody.data.City
import com.example.moody.databinding.FragmentCitiesBinding
import com.example.moody.extensions.BundleArgumentDelegate
import com.example.moody.extensions.withArguments
import com.example.moody.utils.CityAdapter
import retrofit2.Call
import retrofit2.Response

class CityFragment : Fragment(R.layout.fragment_cities) {
    private var drawable: Int = R.drawable.happy
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCitiesBinding.bind(view)
        arguments.let {
            if (it != null) {
                displayCities(binding, it.category)
            }
        }
        binding.backButton.setOnClickListener {
            (activity as MainActivity).goBack()
        }
        binding.movieRecommendation.setOnClickListener {
            arguments?.let { it1 -> (activity as MainActivity).openMoviesScreen(it1.category) }
        }

    }

    private fun setDrawable(category: String) {

        when (category) {
            "Happy" -> {
                drawable = R.drawable.happy
            }
            "Sad" -> {
                drawable = R.drawable.sad
            }
            "Tired" -> {
                drawable = R.drawable.tired
            }
            "Nostalgic" -> {
                drawable = R.drawable.nostalgic
            }
            "Enthusiastic" -> {
                drawable = R.drawable.excited
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayCities(binding: FragmentCitiesBinding, category: String) {

        setDrawable(category)
        binding.mood.text = "Travel ideas: $category "
        binding.mood.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)

        HttpClient.cityService.getCitiesByMood(category).enqueue(
            object : retrofit2.Callback<List<City>> {
                override fun onResponse(
                    call: Call<List<City>>,
                    response: Response<List<City>>
                ) {
                    if (response.isSuccessful) {

                        binding.pager.adapter =
                            response.body()?.shuffled().let {
                                it?.let { it1 ->
                                    CityAdapter(
                                        binding.root.context,
                                        it1
                                    )
                                }
                            }
                    }
                }

                override fun onFailure(call: Call<List<City>>, t: Throwable) {

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
        fun newInstance(category: String) = CityFragment().withArguments {
            it.category = category

        }
    }
}