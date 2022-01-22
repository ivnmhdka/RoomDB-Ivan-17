package com.example.roomdb_ivan_17

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdb_ivan_17.room.Movie
import com.example.roomdb_ivan_17.room.MovieDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { MovieDb(this) }
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val movie = db.movieDao().getMovie()
            Log.d("MainActivity", "dbresponse: $movie")
            withContext(Dispatchers.Main) {
                movieAdapter.setData(movie)
            }
        }
    }

    fun setupListener(){
        add_movie.setOnClickListener{
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(arrayListOf())
        rv_movie.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = movieAdapter
        }
    }
}