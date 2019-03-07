package com.rastogi.prashast.ask_fast.moviedetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rastogi.prashast.ask_fast.R
import com.rastogi.prashast.ask_fast.config.Cast

class CastAdapter(val castList: List<Cast>) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {
    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = castList.get(position)
        holder.bind(cast)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CastViewHolder(layoutInflater!!.inflate(R.layout.item_cast_list, parent, false))
    }

    override fun getItemCount(): Int {
        return castList.count()
    }


    class CastViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(cast: Cast) {
            view.findViewById<TextView>(R.id.cast_profile).text = cast.actorName
            view.findViewById<TextView>(R.id.text_cast_name).text = cast.characterName
        }

    }

}
