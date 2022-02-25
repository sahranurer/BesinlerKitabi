package com.sahraer.besinlerkitabi.adapter

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sahraer.besinlerkitabi.R
import com.sahraer.besinlerkitabi.databinding.RecyclerRowBinding
import com.sahraer.besinlerkitabi.model.Besin
import com.sahraer.besinlerkitabi.util.gorselIndir
import com.sahraer.besinlerkitabi.util.placeHolderYap
import com.sahraer.besinlerkitabi.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.fragment_besin_detay.view.*
import kotlinx.android.synthetic.main.fragment_besin_listesi.view.*
import kotlinx.android.synthetic.main.recycler_row.view.*

class BesinRecyAdapter (val besinListesi:ArrayList<Besin>): RecyclerView.Adapter<BesinRecyAdapter.BesinVH>(),BesinClickListener{
    class BesinVH( var view : RecyclerRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinVH {
       val inflater = LayoutInflater.from(parent.context)
       // val view = inflater.inflate(R.layout.recycler_row,parent,false)
       val view = DataBindingUtil.inflate<RecyclerRowBinding>(inflater,R.layout.recycler_row,parent,false)
        return BesinVH(view)
    }

    override fun onBindViewHolder(holder: BesinVH, position: Int) {

        holder.view.besin = besinListesi[position]
        holder.view.listener = this



       /*
        holder.itemView.besinIsmi.text = besinListesi.get(position).isim
        holder.itemView.besinKalorisi.text = besinListesi.get(position).kalori
        //gorsel-Glide
        holder.itemView.setOnClickListener {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayFragment2(besinListesi.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.itemView.imageView.gorselIndir(besinListesi.get(position).besinGorsel, placeHolderYap(holder.itemView.context))


        */
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    fun besinListesiniGuncelle(yeniBesinListesi:List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }

    override fun besinTiklandi(view: View) {
        val uuid = view.besin_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayFragment2(it)
            Navigation.findNavController(view).navigate(action)

        }

    }

}