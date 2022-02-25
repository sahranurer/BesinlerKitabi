package com.sahraer.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sahraer.besinlerkitabi.model.Besin
import com.sahraer.besinlerkitabi.service.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application) : BaseViewModel(application){
    val besinLiveData =MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid : Int){
launch{
  val dao = BesinDatabase(getApplication()).besinDao()
    val besin = dao.getBesin(uuid)
    besinLiveData.value = besin
}
    }




}