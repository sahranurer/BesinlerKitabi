package com.sahraer.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sahraer.besinlerkitabi.model.Besin
import com.sahraer.besinlerkitabi.service.BesinAPIService
import com.sahraer.besinlerkitabi.service.BesinDatabase
import com.sahraer.besinlerkitabi.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()
    private val besinApiServis = BesinAPIService()
    private val disposible = CompositeDisposable()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())
    private var guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L //dakikanın nanotime çevrilmiş hali

    fun refreshData(){

        val kaydedilmeZamani =  ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            //Sqlite
            verileriSQLiteAl()
        }else{
            verileriInterAl()
        }






    }

    private fun verileriSQLiteAl(){
        launch {
          val besinListesi =   BesinDatabase(getApplication()).besinDao().getAllBesin()
          besinleriGoster(besinListesi)
        }
    }

    private fun verileriInterAl(){
       besinYukleniyor.value = true

        //IO,Default,UI

        disposible.add(
            besinApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                    override fun onSuccess(t: List<Besin>) {
                        //başarılı olursak
                        sqliteSakla(t)

                    }

                    override fun onError(e: Throwable) {
                        //hata alırsak
                        besinHataMesaji.value = true
                        besinYukleniyor.value = false
                        e.printStackTrace()
                    }

                })
        )
    }


    private fun besinleriGoster(besinlerListesi : List<Besin>){
        besinler.value = besinlerListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false
    }

//internetten aldığım tüm verileri database de sakladım
    private fun sqliteSakla(besinListesi : List<Besin>){
       launch {
           val dao = BesinDatabase(getApplication()).besinDao()
           dao.deleteAllBesin()
           val uuidListesi = dao.insertAll(*besinListesi.toTypedArray()) // tek tek dao içerine verdim
           var i = 0
           while (i<besinListesi.size){
               besinListesi[i].uuid = uuidListesi[i].toInt()
               i = i + 1
           }

           besinleriGoster(besinListesi)
       }

      ozelSharedPreferences.zamaniKaydet(System.nanoTime())


    }





}