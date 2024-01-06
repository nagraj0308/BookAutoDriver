package com.book.admin.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.book.admin.R
import com.book.admin.data.remote.reqres.Homestay
import com.book.admin.utils.Utils
import com.bumptech.glide.Glide


class HomestayAdapter(private val mList: List<Homestay>, val nc: NavController) :
    RecyclerView.Adapter<HomestayAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_homestay, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.tvName.text = item.name
        holder.tvMobile.text = item.mobileNo
        holder.tvDate.text = Utils.convertLongToTime(item.modifyTime)
        Glide.with(holder.iv.context).asBitmap().load(item.imageUrl1).into(holder.iv)
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("item", mList[position])
            nc.navigate(R.id.nav_view_homestay, bundle)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv: ImageView = itemView.findViewById(R.id.iv)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvMobile: TextView = itemView.findViewById(R.id.tv_mobile)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
    }
}