package com.example.earthquake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val baseUrl = "https://earthquake.usgs.gov/fdsnws/event/1/"
    private lateinit var myRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRecyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.loading_indicator)
        progressBar.visibility = View.VISIBLE


        val api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val filter = HashMap<String, String>()
        filter["format"] = "geojson"
        filter["limit"] = "20"
        filter["minmagnitude"] = "5"

        val requestCall = api.getData(filter)

        requestCall.enqueue(object : Callback<MyData> {
            override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                if (response.isSuccessful) {
                    val myData = response.body()!!.features
                    Log.d(TAG, "onResponse: successful: $myData")

                    myAdapter = MyAdapter(this@MainActivity, myData)
                    myRecyclerView.adapter = myAdapter
                    myRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                } else {
                    Log.d(TAG, "onResponse: failed")
                    Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<MyData>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                Toast.makeText(this@MainActivity, "Failed ${t.message}", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            }
        })
    }
}