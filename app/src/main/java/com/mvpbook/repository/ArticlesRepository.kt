
package com.mvpbook.repository

import android.util.Base64
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.google.gson.GsonBuilder
import com.mvpbook.strorage.ArticlesDAO
import com.mvpbook.strorage.ArticlesItem


class ArticlesRepository private constructor(private val articlesDAO: ArticlesDAO) {
    private val AUTH = "Basic "+ Base64.encodeToString("belalkhan:123456".toByteArray(), Base64.NO_WRAP)

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: ArticlesRepository? = null

        fun getInstance(databaseDAO: ArticlesDAO): ArticlesRepository = instance
                ?: synchronized(this) {
                    instance
                            ?: ArticlesRepository(databaseDAO).also { instance = it }
                }
    }

    fun getAll() = articlesDAO.getAll()

    fun insertAll(articlesItemList: List<ArticlesItem>) {
        doAsync {
            articlesDAO.insertAll(articlesItemList)
        }
    }

    fun deleteAll() {
        doAsync { articlesDAO.deleteAll() }

    }



    fun requestArticles() {
        AndroidNetworking.get("http://f0312303.xsph.ru/public/authors")
                .addHeaders("Authorization", AUTH)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray?) {
                        Log.e("N21",response?.length().toString())
                            val size = response?.length()
                            val articlesItemList: MutableList<ArticlesItem>? = mutableListOf()
//

                            if(size!! >1) for (i in 0..response.length()-1){
                                val body = response[i]?.toString()
                                val gson = GsonBuilder().create()
                                val book = gson.fromJson(body, ArticlesItem::class.java)
                                articlesItemList?.add(book)

                                Log.e("N-"+i.toString(),response[i].toString())
                            }
                        if (articlesItemList != null && !articlesItemList.isEmpty()) {
                            insertAll(articlesItemList)
                        }



                    }

                    override fun onError(anError: ANError?) {
                        Log.e("N21",anError.toString())

                    }
                })





    }

}
