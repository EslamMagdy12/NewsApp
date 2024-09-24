package com.example.newsapp.news.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.news.network.APIClient
import com.example.newsapp.news.network.db.LocalDataSourceImplementation
import com.example.newsapp.news.repo.NewsRepositoryImplementation
import com.example.newsapp.news.viewmodel.NewsViewModel
import com.example.newsapp.news.viewmodel.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private val articleFragmentArgs: ArticleFragmentArgs by navArgs()
    private var isFavorite: Boolean = false // Track favorite status here

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        getViewModel()

        val article = articleFragmentArgs.article
        val webView = view.findViewById<WebView>(R.id.webView)
        Log.d("ArticleFragment", "Loading article: ${article.id} - ${article.title}")
        webView.webViewClient = WebViewClient()
        webView.loadUrl(article.url)

        val imageButton = view.findViewById<ImageButton>(R.id.imageButton)

        // Observe favorite status to update the UI and track the status
        viewModel.isFavorite(article).observe(viewLifecycleOwner) { isFav ->
            isFavorite = isFav // Track the current favorite status
            if (isFav) {
                imageButton.setImageResource(R.drawable.favorite_icon)
            } else {
                imageButton.setImageResource(R.drawable.not_favorite_icon)
            }
        }

        // Toggle favorite status when the button is clicked
        imageButton.setOnClickListener {
            if (isFavorite) {
                // Store the article to undo
                viewModel.delete(article) // Remove from favorites
                Log.d("ArticleFragment", "Article deleted from favorites")

                // Show Snackbar with undo option
                Snackbar.make(view, "Removed from favorites", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        article.let {
                            viewModel.insert(it) // Re-insert the article into the database
                            Log.d("ArticleFragment", "Article restored to favorites")
                        }
                    }.show()
            } else {
                viewModel.insert(article) // Add to favorites
                Log.d("ArticleFragment", "Article added to favorites")
                Snackbar.make(view, "Added to favorites", Snackbar.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun getViewModel() {
        val newsViewModelFactory = NewsViewModelFactory(
            repository = NewsRepositoryImplementation(
                localDataSource = LocalDataSourceImplementation(requireContext()),
                remoteDataSource = APIClient
            )
        )
        viewModel = ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel::class.java)
    }
}
