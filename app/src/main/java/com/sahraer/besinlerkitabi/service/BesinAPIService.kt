package com.sahraer.besinlerkitabi.service

import com.sahraer.besinlerkitabi.model.Besin
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BesinAPIService {

    //https://raw.githubusercontent.com
    //atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json


    private val BASE_URL ="https://raw.githubusercontent.com/"
    private val api = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) //json modele çevirme işlemi
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(BesinAPI::class.java)


    fun getData() : Single<List<Besin>>{
          return api.getBesin()
    }


}