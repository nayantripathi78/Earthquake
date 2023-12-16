package com.example.earthquake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val baseUrl = "https://earthquake.usgs.gov/fdsnws/event/1/"
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRecyclerView = findViewById(R.id.recyclerView)

        CoroutineScope(Dispatchers.IO).launch {
            val api = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)

            val myResponse = api.getData("geojson", "10", "5")

            withContext(Dispatchers.Main) {
                if (myResponse.isSuccessful) {
                    val myData = myResponse.body()!!.features
                    Log.d(TAG, "onResponse: successful: $myData")

                    myAdapter = MyAdapter(this@MainActivity, myData)
                    myRecyclerView.adapter = myAdapter
                    myRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                } else {
                    Log.d(TAG, "onResponse: failed")
                    Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}