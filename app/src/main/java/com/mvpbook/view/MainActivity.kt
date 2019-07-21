package com.mvpbook.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import android.widget.LinearLayout
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.mvpbook.R
import com.mvpbook.bold
import com.mvpbook.regular
import com.mvpbook.repository.ArticlesRepository
import com.mvpbook.strorage.AppDatabase
import com.mvpbook.strorage.SharedPrefManager
import com.mvpbook.viewmodel.ArticlesViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var articlesViewModel: ArticlesViewModel
    private var screen:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        profileView.setOnClickListener {
//            SharedPrefManager.getInstance(this).clear()
//            finish()
            val intent = Intent(applicationContext, ProfileActivity::class.java)

            intent.flags =
                (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) and (Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivity(intent)
//            finish();

        }



        textauthor.typeface = bold(this)
        textitle.typeface = regular(this)


        val factory: ArticlesViewModel.Factory =
            ArticlesViewModel.Factory(ArticlesRepository.getInstance(AppDatabase.getInstance(this).getArticlesDAO()))

        articlesViewModel = ViewModelProviders.of(this, factory).get(ArticlesViewModel::class.java)
        articlesViewModel.deleteAll()
        articlesViewModel.requestArticles()

        val url = "https://pp.userapi.com/c837133/v837133231/21b23/7FWjsNmv62Q.jpg"
        Glide.with(this).load(url).apply(RequestOptions.circleCropTransform()).into(profileView)



        mainRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)






        articlesViewModel.getAll().observe(this, Observer { articles ->
            if (articles != null && articles.toString() != "[]") {
                setView(articles[0].author, articles[0].title, articles[0].description, articles[0].img)
                articlesViewModel.setVisibily(false, ContentLayout)
                screen = 0
                Log.e("nonull", articles.toString())
                val adapter = ArticlesListAdapter(articles) { adapterPosition ->
                    var adapterposition: Int = adapterPosition

                    if (adapterPosition < 0) {
                        toast("longclick")
                        adapterposition = adapterPosition * -1 - 1

                        articlesViewModel.setVisibily(true, ContentLayout)
                        screen = 1
                        articlesViewModel.loadBackgroundImage(
                            ContentLayout,
                            FrontLayout,
                            articles[adapterposition].img,
                            1
                        )
                        articlesViewModel.fronactivity(articles[adapterposition], this, FrontLayout)


                    }
                    toast(articles[adapterposition].title + '\n' + articles[adapterposition].author)
                    val text = articles[adapterposition]
                    setView(text.author, text.title, text.description, text.img)
                }
                mainRecyclerView.adapter = adapter


            } else {
                setView("No internet", "Please refresh", "to update", "")
            }
        })


        back_btn.setOnClickListener {
            articlesViewModel.setVisibily(
                false,
                ContentLayout
            ); articlesViewModel.loadBackgroundImage(ContentLayout, FrontLayout, "", 0)
            screen = 0
        }
    }

    fun setView(author: String, title: String, description: String, img: String) {


        textitle.setText(title)
        textauthor.setText(author)
        textdescription.setText(description)
    }

    override fun onStart() {
        super.onStart()


        if (SharedPrefManager.getInstance(this).isreg.equals(0)) {
            val intent = Intent(applicationContext, LoginActivity::class.java)

            intent.flags =
                (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) and (Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivity(intent)
            finish();
        }
    }

    override fun onBackPressed() {
            if(screen == 1){
                toast("N21")
                articlesViewModel.setVisibily(false,ContentLayout)
                articlesViewModel.loadBackgroundImage(ContentLayout, FrontLayout, "", 0)
                screen = 0
                return
            }
            if(screen ==  0) {
                toast("N22")
                super.onBackPressed()
                return
            }
    }

}

