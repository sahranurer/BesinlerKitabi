package com.sahraer.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sahraer.besinlerkitabi.R
import com.sahraer.besinlerkitabi.adapter.BesinRecyAdapter
import com.sahraer.besinlerkitabi.viewmodel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_listesi.*


class BesinListesiFragment : Fragment() {

    private lateinit var viewModel : BesinListesiViewModel
    private val recylerBesinAdapter = BesinRecyAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //viewmodel bağlama
        viewModel =
            ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)
        viewModel.refreshData()

        besinListRecycler.layoutManager = LinearLayoutManager(context)
        besinListRecycler.adapter = recylerBesinAdapter

        swipeRefreshLayout.setOnRefreshListener {
            besinYukleniyor.visibility = View.VISIBLE
            besinListRecycler.visibility = View.GONE
            besinHataMesaji.visibility = View.GONE

              viewModel.refreshData()
            swipeRefreshLayout.isRefreshing = false
        }





        observeLiveData()


    }

fun observeLiveData(){
    viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler ->
        besinler?.let {

            besinListRecycler.visibility = View.VISIBLE
            recylerBesinAdapter.besinListesiniGuncelle(besinler)

        }

    })

    viewModel.besinHataMesaji.observe(viewLifecycleOwner, Observer { hata->
        hata?.let {
            if (it){
                besinHataMesaji.visibility = View.VISIBLE
                besinListRecycler.visibility = View.GONE
            }else{
                besinHataMesaji.visibility = View.GONE
            }
        }

    })

    viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor ->
        yukleniyor?.let {
            if (it){
                besinListRecycler.visibility = View.GONE
                besinHataMesaji.visibility = View.GONE
                besinYukleniyor.visibility = View.VISIBLE
            }else{
                besinYukleniyor.visibility = View.GONE
            }
        }

    })



}




}