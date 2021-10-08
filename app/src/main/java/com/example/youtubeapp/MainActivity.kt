package com.example.youtubeapp


import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var rvMain: RecyclerView
    lateinit var videosList: MutableList<MutableMap<String, Any>>
    lateinit var adapter: VideoRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("Alsultan", MODE_PRIVATE)

        videosList = arrayListOf(
            mutableMapOf(
                "Title" to "How to become a manager? ",
                "Title2" to "How to become a manager? ",
                "Views" to sharedPreferences.getInt("ViewsVid0", 0),
                "ID" to "ZbZFMDk69IA"
            ),
            mutableMapOf(
                "Title" to "How to become a manager ? ",
                "Title2" to "How to become a manager 2? ",
                "Views" to sharedPreferences.getInt("ViewsVid1", 0),
                "ID" to "G_XYXuC8b9M"
            )
        )

        rvMain = findViewById(R.id.rvMain)
        adapter = VideoRecyclerViewAdapter(videosList, sharedPreferences)
        rvMain.adapter = adapter
        rvMain.layoutManager = LinearLayoutManager(this)

        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        if(activeNetwork?.isConnectedOrConnecting == true) {
            fetchImage()
            rvMain.visibility = View.VISIBLE
        }
        else
            rvMain.visibility = View.GONE
    }


    fun update(position: Int, views: Int){
        Log.d("ppp", views.toString())
        videosList[position]["Views"] = views
        adapter.notifyDataSetChanged()
    }

    private fun fetchImage(){
        CoroutineScope(IO).launch {
            for(i in 0..videosList.size-1){
                try {
                    val response = URL()
                }catch (e: Exception){
                    videosList[i]["Image_url"] = R.drawable.iic
                }
            }
        }
    }
}