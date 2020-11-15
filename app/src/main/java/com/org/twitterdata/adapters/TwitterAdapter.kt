package com.org.twitterdata.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.org.twitterdata.R
import com.org.twitterdata.db.entity.TwitterData

class TwitterAdapter(  items: List<TwitterData>,
                       context: Context
) : RecyclerView.Adapter<TwitterAdapter.TwitterViewHolder>() {

    private var items = ArrayList<TwitterData>()
    private var context: Context

    fun setItems(items: ArrayList<TwitterData>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitterViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_data, parent, false)
        return TwitterViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    inner class TwitterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvTwitterName: TextView
        var tvTwitterHandle: TextView
        var tvTweet: TextView
        var tvRetweet: TextView
        var tvFavTweets: TextView
        var imgUserProfile: AppCompatImageView

        init {
            tvTwitterName = itemView.findViewById(R.id.tvTwitterName)
            tvTwitterHandle = itemView.findViewById(R.id.tvHandleName)
            tvTweet = itemView.findViewById(R.id.tvTweet)
            tvRetweet = itemView.findViewById(R.id.tvRetweet)
            tvFavTweets = itemView.findViewById(R.id.tvFavTweets)
            imgUserProfile = itemView.findViewById(R.id.imgUser)
        }
    }

    init {
        this.items = items as ArrayList<TwitterData>
        this.context = context
    }

    override fun onBindViewHolder(holder: TwitterAdapter.TwitterViewHolder, position: Int) {

        var tweetDetails = items.get(position)
        Glide.with(context)
            .load(tweetDetails.profileImageUrl)
            .transform(CircleCrop())
            .placeholder(R.drawable.ic_twitter_logo_blue)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imgUserProfile)

        holder.tvTwitterName.setText(tweetDetails.name)
        holder.tvTwitterHandle.setText(tweetDetails.handle)
        holder.tvFavTweets.setText(tweetDetails.favoriteCount)
        holder.tvRetweet.setText(tweetDetails.retweetCount)
        holder.tvTweet.setText(tweetDetails.text)
    }
}