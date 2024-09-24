package com.example.newsapp.news.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.NewsAdapter
import com.example.newsapp.R
import com.example.newsapp.news.network.APIClient
import com.example.newsapp.news.network.db.LocalDataSourceImplementation
import com.example.newsapp.news.repo.NewsRepositoryImplementation
import com.example.newsapp.news.viewmodel.NewsViewModel
import com.example.newsapp.news.viewmodel.NewsViewModelFactory

class SearchFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.searchRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        getViewModel()
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query != "") {
                    viewModel.getNewsByQuery(query)
                    viewModel.news.observe(viewLifecycleOwner) {
                        recyclerView.adapter =
                            NewsAdapter(it.articles, onArticleClick = { article ->
                                val action =
                                    SearchFragmentDirections.actionSearchFragmentToArticleFragment(
                                        article
                                    )
                                requireView().findNavController().navigate(action)
                            })
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText != "") {
                    viewModel.getNewsByQuery(newText)
                    viewModel.news.observe(viewLifecycleOwner) {
                        recyclerView.adapter =
                            NewsAdapter(it.articles, onArticleClick = { article ->
                                val action =
                                    SearchFragmentDirections.actionSearchFragmentToArticleFragment(
                                        article
                                    )
                                requireView().findNavController().navigate(action)
                            })
                    }
                }
                return true
            }
        })
        return view
    }

    fun getViewModel() {
        val newsViewModelFactory = NewsViewModelFactory(
            repository = NewsRepositoryImplementation(
                localDataSource = LocalDataSourceImplementation(requireContext()),
                remoteDataSource = APIClient
            )
        )
        viewModel = ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel::class.java)
    }

}