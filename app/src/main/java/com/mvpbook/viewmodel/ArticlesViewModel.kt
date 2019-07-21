

package com.mvpbook.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.mvpbook.R
import com.mvpbook.bold
import com.mvpbook.light
import com.mvpbook.regular
import com.mvpbook.repository.ArticlesRepository
import com.mvpbook.strorage.ArticlesItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class ArticlesViewModel internal constructor(private val articlesRepository: ArticlesRepository) : ViewModel() {

    init {
        requestArticles()


    }

    fun requestArticles() = articlesRepository.requestArticles()


    fun getAll() = articlesRepository.getAll()

    fun insertAll(articlesItemList: List<ArticlesItem>) {
        articlesRepository.insertAll(articlesItemList)
    }

    fun deleteAll() {
        articlesRepository.deleteAll()
    }


    @SuppressLint("SetTextI18n")
    fun fronactivity(articlesItem: ArticlesItem, context: Context, FrontLayout: View){
        FrontLayout.txtauthor.text = articlesItem.author
        FrontLayout.date.text = articlesItem.date
        FrontLayout.rating.text = articlesItem.rating.toString() + "★"
        FrontLayout.txtcost.text = "\$" + articlesItem.cost

        FrontLayout.txtauthor.typeface = bold(context)
        FrontLayout.date.typeface = regular(context)
        FrontLayout.rating.typeface = regular(context)
        FrontLayout.published.typeface = light(context)
        FrontLayout.ratingup.typeface = light(context)
        FrontLayout.txtcost.typeface = bold(context)

    }
    fun setVisibily(isVisible:Boolean,ContentLayout:View){
        if (isVisible) {
            ContentLayout.BackLayout.visibility = View.GONE
            ContentLayout.FrontLayout.visibility = View.VISIBLE
        } else {
            ContentLayout.LoadLayout.visibility = View.GONE
            ContentLayout.BackLayout.visibility = View.VISIBLE
            ContentLayout.FrontLayout.visibility = View.GONE


        }
    }
    fun loadBackgroundImage(ContentLayout:View, view: LinearLayout, imageUrl: String?, isClean: Int){


        if (isClean.equals(1)) {
            Glide.with(view.context).load(imageUrl).centerInside()
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        view.background = resource
                    }
                })

        } else {
            Glide.with(view.context).load(R.drawable.transparly).fitCenter().centerInside()
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        view.background = resource
                    }
                })

        }

    }



    class Factory(private val articlesRepository: ArticlesRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) = ArticlesViewModel(articlesRepository) as T
    }
}