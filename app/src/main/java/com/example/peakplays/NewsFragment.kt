package com.example.peakplays

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.peakplays.adapters.NewsAdapter
import com.example.peakplays.api.NewsApiService
import com.example.peakplays.databinding.FragmentNewsBinding
import com.example.peakplays.models.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter
    
    private val apiKey = "aab2be87f11942ddbbc94cd85f281e3a"
    private val baseUrl = "https://newsapi.org/v2/"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        // Force the root layout to be visible
        binding.root.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("NewsFragment", "onViewCreated called")
        
        // Ensure the fragment's view is visible
        view.visibility = View.VISIBLE
        
        setupRecyclerView()
        fetchSportsNews()
    }

    private fun setupRecyclerView() {
        Log.d("NewsFragment", "Setting up RecyclerView")
        
        // Force RecyclerView to be visible
        binding.newsRecyclerView.visibility = View.VISIBLE
        
        newsAdapter = NewsAdapter().apply {
            Log.d("NewsFragment", "Created new NewsAdapter")
        }
        
        binding.newsRecyclerView.apply {
            Log.d("NewsFragment", "Configuring RecyclerView")
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
            
            // Add item decoration for visual separation
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            )
            
            // Force measurement and layout
            post {
                requestLayout()
                Log.d("NewsFragment", "Forced RecyclerView layout")
            }
        }
        
        Log.d("NewsFragment", "RecyclerView setup complete")
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun fetchSportsNews() {
        Log.d("NewsFragment", "Starting API call")
        binding.progressBar.visibility = View.VISIBLE

        if (!isNetworkAvailable()) {
            showError("No internet connection available")
            Toast.makeText(
                context,
                "Please check your internet connection and try again",
                Toast.LENGTH_LONG
            ).show()
            binding.progressBar.visibility = View.GONE
            return
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(NewsApiService::class.java)

        try {
            newsService.getSportsNews(apiKey, "us", "sports")
                .enqueue(object : Callback<NewsResponse> {
                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                        binding.progressBar.visibility = View.GONE
                        
                        if (response.isSuccessful) {
                            val newsResponse = response.body()
                            Log.d("NewsFragment", "Articles received: ${newsResponse?.articles?.size}")
                            
                            if (newsResponse?.articles.isNullOrEmpty()) {
                                showError("No news articles found")
                                return
                            }

                            newsResponse?.articles?.let { articles ->
                                Log.d("NewsFragment", "Submitting ${articles.size} articles to adapter")
                                activity?.runOnUiThread {
                                    // Force RecyclerView to be visible again
                                    binding.newsRecyclerView.visibility = View.VISIBLE
                                    
                                    newsAdapter.submitList(articles)
                                    
                                    // Force a layout pass
                                    binding.newsRecyclerView.post {
                                        binding.newsRecyclerView.requestLayout()
                                        Log.d("NewsFragment", "Forced layout after submitting articles")
                                    }
                                }
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            Log.e("NewsFragment", "Error response: $errorBody")
                            showError("Error: ${response.code()} - ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        binding.progressBar.visibility = View.GONE
                        Log.e("NewsFragment", "Network error", t)
                        Log.e("NewsFragment", "Network error details: ${t.message}")
                        showError("Network error: ${t.message}")
                        
                        // Show a more user-friendly error and solution
                        activity?.runOnUiThread {
                            Toast.makeText(
                                context,
                                "Cannot connect to the internet. Please check your connection and try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
        } catch (e: Exception) {
            Log.e("NewsFragment", "Exception when making API call", e)
            showError("Error: ${e.message}")
        }
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        Log.e("NewsFragment", "Error: $message")
    }

    private fun testNetworkConnection() {
        val url = "https://www.google.com"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("NewsFragment", "Test connection failed", e)
                activity?.runOnUiThread {
                    Toast.makeText(context, "Internet connection test failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                Log.d("NewsFragment", "Test connection successful: ${response.code}")
                fetchSportsNews()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 