package com.mvpbook.view

import android.annotation.SuppressLint
//import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_view.view.*
//import android.arch.persistence.room.Dao
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.mvpbook.R
import com.mvpbook.inflate
import com.mvpbook.strorage.ArticlesItem


class ArticlesListAdapter(private val arrNewsUpdates: List<ArticlesItem>, private val listener: (Int) -> Unit) : RecyclerView.Adapter<ArticlesListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(inflate(parent.context, R.layout.item_view, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(arrNewsUpdates[position], listener)
    }

    override fun getItemCount(): Int {
        return arrNewsUpdates.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(articlesItem: ArticlesItem, listener: (Int) -> Unit) = with(itemView) {
            textViewTitle.text =    articlesItem.title
            textViewAuthor.text =   articlesItem.author
//            date.text =             articlesItem.rating.toString()
            Log.e("date",articlesItem.rating.toString())


            textViewDescription.text = articlesItem.description.substring(0,shufle(articlesItem)) + "..."


            loadBackgroundImage(itemView.imageView2,articlesItem.img)


            itemView.setOnClickListener { listener(adapterPosition) }
            itemView.setOnLongClickListener {
                Log.e("digital",((adapterPosition+1)*-1).toString())
                listener((adapterPosition+1)*-1)
                return@setOnLongClickListener true
            }
        }
    }


}
fun shufle(it: ArticlesItem): Int {
    var size = 0
    for (i in 150..it.description.length-1){
        if(it.description[i].equals(' ')) {

            size = i; break
        }}
    return size
}
fun loadBackgroundImage(view: ImageView, imageUrl:String?) {


    Glide.with(view.context).load(imageUrl).override(523,930).centerCrop().centerInside().into(object: SimpleTarget<Drawable>()
    {
        override fun onResourceReady(resource: Drawable, transition: Transition<in
        Drawable>?)
        {

                view.background = resource
        }
    })
}
