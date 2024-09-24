package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsapp.news.network.dto.Article

class NewsAdapter(var data: List<Article>, val onArticleClick: (Article) -> Unit) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView? = null
        private val description: TextView? = null
        private val image: ImageView? = null
        private val source: TextView? = null
        private val dateTime: TextView? = null

        fun getTitle(): TextView? {
            return title ?: itemView.findViewById(R.id.newsTitleTextView)
        }

        fun getDescription(): TextView? {
            return description ?: itemView.findViewById(R.id.newsDescriptionTextView)
        }

        fun getImage(): ImageView? {
            return image ?: itemView.findViewById(R.id.newsImageView)
        }

        fun getSource(): TextView? {
            return source ?: itemView.findViewById(R.id.newsSourceTextView)
        }

        fun getDateTime(): TextView? {
            return dateTime ?: itemView.findViewById(R.id.newsDateTimeTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_news_tile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getTitle()?.text = data[position].title ?: "No title"
        holder.getDescription()?.text = data[position].description ?: "No description"
        holder.getSource()?.text = data[position].source.name
        holder.getDateTime()?.text = data[position].publishedAt
        Glide.with(holder.itemView.context)
            .load(data[position].urlToImage ?: R.drawable.no_image).apply(
            RequestOptions()
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
        )
            .into(holder.getImage()!!)
        holder.itemView.setOnClickListener {
            onArticleClick(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


}