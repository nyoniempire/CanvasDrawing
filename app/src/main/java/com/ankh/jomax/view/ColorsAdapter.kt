package com.ankh.jomax.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ankh.jomax.R
import com.ankh.jomax.data.PenColors

class ColorsAdapter(
    private val context: Context,
    private val colors: ArrayList<PenColors>,
    val clickHandler: (color: String)->Unit)
    : RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder>() {

    class ColorsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val colorItem: View = itemView.findViewById(R.id.view_my_canvas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
        return ColorsViewHolder(LayoutInflater.from(context).inflate(R.layout.colors_canvas,parent,false))
    }

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        val gbColor: GradientDrawable = holder.colorItem.background as GradientDrawable
        gbColor.setColor(Color.parseColor(colors[position].value))

        holder.colorItem.setOnClickListener {
            clickHandler(colors[position].value)
        }
    }

    override fun getItemCount(): Int {
        return colors.size
    }
}