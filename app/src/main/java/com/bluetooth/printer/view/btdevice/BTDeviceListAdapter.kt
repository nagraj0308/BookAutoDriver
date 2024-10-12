package com.bluetooth.printer.view.btdevice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bluetooth.printer.R
import com.bluetooth.printer.data.BTDevice
import com.bumptech.glide.Glide

class BTDeviceListAdapter(
    private val mList: List<BTDevice>,
    private val onClickListener: (View, BTDevice) -> Unit
) :
    RecyclerView.Adapter<BTDeviceListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_print_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        Glide.with(holder.iv.context).asBitmap().load(item.resId).into(holder.iv)
        holder.tvName.text = item.name;

        holder.itemView.setOnClickListener { view ->
            onClickListener.invoke(view, item)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv: ImageView = itemView.findViewById(R.id.iv_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
    }
}