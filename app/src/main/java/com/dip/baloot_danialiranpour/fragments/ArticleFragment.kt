package com.dip.baloot_danialiranpour.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dip.baloot_danialiranpour.R
import com.dip.baloot_danialiranpour.ui.MyAdapter
import com.dip.baloot_danialiranpour.viewmodels.ArticleViewModel
import androidx.navigation.fragment.findNavController
import com.dip.baloot_danialiranpour.api.Resource
import com.dip.baloot_danialiranpour.databinding.FragmentArticleBinding
import com.dip.baloot_danialiranpour.utils.Utils.PAGE_SIZE

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: ArticleViewModel
    lateinit var articleAdapter: MyAdapter
    lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)


        viewModel = ViewModelProvider(requireActivity()).get(ArticleViewModel::class.java)

        articleAdapter = MyAdapter()
        binding.rvArticles.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@ArticleFragment.scrollListener)
        }

//        articleAdapter.setOnItemClickListener {
////            findNavController().navigate(
////                R.id.action_articleFragment_to_profileFragment
////            )
//        }

        viewModel.articles.observe(requireActivity(), Observer { response ->
            when (response) {

                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        articleAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = (newsResponse.totalResults?.div(PAGE_SIZE) ?: 0) + 2
                        isLastPage = viewModel.articlePage == totalPages

                    }
                }
                is Resource.Error -> {

                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("danitest", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })


    }


    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.getArticles()
                isScrolling = false
            } else {
                binding.rvArticles.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

}