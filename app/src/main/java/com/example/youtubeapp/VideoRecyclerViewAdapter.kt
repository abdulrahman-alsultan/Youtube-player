package com.example.youtubeapp

import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.youtube_item_row.view.*


class VideoRecyclerViewAdapter(private val videoList: List<Map<String, Any>>, val sharedPreferences: SharedPreferences): RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewItemHolder>() {

    val selectedVideoToPlay = arrayListOf<Int>()
    var canPlayVideos = false
    var currentVideo = -1

    inner class ViewItemHolder(itemView: View): RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemHolder {
        return ViewItemHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.youtube_item_row,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewItemHolder, position: Int) {
        val video = videoList[position]

        holder.itemView.apply {
            video_title2.text = video["Title2"].toString()
            video_views.text = "Views: ${video["Views"]}"

            card_view.setOnClickListener {
                if(position != currentVideo){
                    currentVideo = position
                    val views = video["Views"].toString().toInt() + 1
                    sharedPreferences.edit().putInt("ViewsVid$position", views).apply()
                    val activity = context as MainActivity
                    activity.videosList[position]["Views"] = views
                    activity.update(position, views)
                    val intent = Intent(holder.itemView.context, PlayVideo::class.java)
                    intent.putExtra("ID", video["ID"].toString())
                    holder.itemView.context.startActivity(intent)
                }
            }

            video_checkbox.setOnClickListener {
                if(video_checkbox.isChecked)
                    selectedVideoToPlay.add(position)
                else
                    selectedVideoToPlay.remove(position)

                canPlayVideos = selectedVideoToPlay.size > 0
            }

        }
    }

    override fun getItemCount(): Int = videoList.size
}