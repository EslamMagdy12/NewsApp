package com.example.newsapp.news.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.NewsAdapter
import com.example.newsapp.R
import com.example.newsapp.news.network.APIClient
import com.example.newsapp.news.network.db.LocalDataSourceImplementation
import com.example.newsapp.news.network.dto.Article
import com.example.newsapp.news.repo.NewsRepositoryImplementation
import com.example.newsapp.news.viewmodel.NewsViewModel
import com.example.newsapp.news.viewmodel.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private var deletedArticle: Article? = null // Temporary storage for deleted article
    private lateinit var adapter: NewsAdapter
    private var articles: MutableList<Article> = mutableListOf() // Mutable list for articles

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.favouritesRecyclerView)
        getViewModel()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = NewsAdapter(articles) { article ->
            val action = FavoritesFragmentDirections.actionFavouritesFragmentToArticleFragment(article)
            requireView().findNavController().navigate(action)
        }

        recyclerView.adapter = adapter

        viewModel.getArticles()
        viewModel.articles.observe(viewLifecycleOwner) {
            articles.clear()
            articles.addAll(it) // Update mutable list with new data
            adapter.notifyDataSetChanged() // Notify adapter of data change
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = articles[position]

                // Store the deleted article temporarily
                deletedArticle = article

                // Remove the article from the view model (and database)
                viewModel.delete(article)

                // Remove the article from the mutable list
                articles.removeAt(position)

                // Notify the adapter that the item was removed
                adapter.notifyItemRemoved(position)

                // Show Snackbar with restore option
                Snackbar.make(view, "Article deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        deletedArticle?.let {
                            viewModel.insert(it) // Re-insert the article into the database
                            articles.add(position, it) // Restore in the mutable list
                            adapter.notifyItemInserted(position) // Notify adapter
                        }
                    }
                    .show()
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)

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
