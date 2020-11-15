package com.org.twitterdata.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.org.twitterdata.R
import com.org.twitterdata.adapters.TwitterAdapter
import com.org.twitterdata.db.entity.TwitterData
import com.org.twitterdata.utils.Resource
import com.tcs.mobile.ionapp17.viewmodel.TwitterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var twitterAdapter: TwitterAdapter
    private val twitterViewModel: TwitterViewModel by viewModels()
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var btnSearch : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        fetchData()
    }

    private fun initViews() {
        twitterAdapter = TwitterAdapter(ArrayList(), this)
        swipeRefreshLayout = findViewById(R.id.swipeRefresh)
        searchView = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)
        recyclerView = findViewById(R.id.rView)
        progressBar = findViewById(R.id.progress_bar)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setItemAnimator(DefaultItemAnimator())
        recyclerView.adapter = twitterAdapter
        swipeRefreshLayout.setOnRefreshListener {
            fetchData()
            swipeRefreshLayout.isRefreshing = false
        }
        btnSearch.setOnClickListener{
            fetchQueryDataFromDb(searchView.query.toString())
        }
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                fetchQueryDataFromDb(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                fetchQueryDataFromDb(newText)
                return false
            }
        })
    }

    private fun fetchData() {
        twitterViewModel.twitterData.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) {
                        twitterAdapter.setItems(ArrayList(it.data))
                    }
                }
                Resource.Status.ERROR ->
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    progressBar.visibility = View.VISIBLE
            }
        })
    }
    private fun fetchQueryDataFromDb(query: String)
    {
        twitterViewModel.getQueryData(query).observe(
            this,
            object : Observer<List<TwitterData>> {
                override fun onChanged(listData: List<TwitterData>?) {

                    if (listData == null){
                        fetchData()
                    }else{
                        twitterAdapter.setItems(listData as ArrayList<TwitterData>)
                    }
                }
            }
        )
    }
}
