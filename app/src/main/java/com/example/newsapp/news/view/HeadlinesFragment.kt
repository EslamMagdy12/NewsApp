package com.example.newsapp.news.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.NewsAdapter
import com.example.newsapp.R
import com.example.newsapp.news.network.APIClient
import com.example.newsapp.news.network.db.LocalDataSource
import com.example.newsapp.news.network.db.LocalDataSourceImplementation
import com.example.newsapp.news.repo.NewsRepository
import com.example.newsapp.news.repo.NewsRepositoryImplementation
import com.example.newsapp.news.viewmodel.NewsViewModel
import com.example.newsapp.news.viewmodel.NewsViewModelFactory

class HeadlinesFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_headlines, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.headlinesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        getViewModel()
        viewModel.getNews()
        viewModel.news.observe(viewLifecycleOwner) {
            recyclerView.adapter = NewsAdapter(it.articles, onArticleClick = { article ->
                val action =
                    HeadlinesFragmentDirections.actionHeadlinesFragmentToArticleFragment(article)
                requireView().findNavController().navigate(action)
            })
        }


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